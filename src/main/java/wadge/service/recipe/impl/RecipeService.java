package wadge.service.recipe.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import wadge.dao.RecipeRepository;
import wadge.model.recipe.Recipe;
import wadge.service.food.FoodService;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.api.AbstractRecipeSelection;
import wadge.service.recipe.api.IRecipeSelection;
import wadge.service.recipe.impl.RecipeSelection.Parameter;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final FridgeService fridgeService;
    private final FoodService foodService;

    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    final ToIntFunction<Recipe> recipeScoring = recipe -> recipe.getIngredients().stream().map(SelectionWithFridge.ingredientScoring)
            .filter(Optional::isPresent).map(Optional::get).reduce(0, (a, b) -> a + b);

    public List<Recipe> getRecipesUsingFridge() {
        final AbstractRecipeSelection sel = new SelectionWithFridge(getAllRecipes()
                .stream().collect(Collectors.toSet()), fridgeService);
        return sel.compute(recipeScoring).
                filter(x -> x > 0).sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
    }

    public Map<String, String> getRecipeIngredient(final Recipe recipe) {
        return recipe.getIngredients().stream().collect(Collectors.toMap(
                ingredient -> ingredient.getName(),
                ingredient -> fridgeService.isInFridge(ingredient)));
    }

    // TODO -> refactor
    public List<Recipe> selectRecipes(final Parameter param) {
        if(param.equals(Parameter.USING_FRIDGE)) {
            return getRecipesUsingFridge(); 
        }

        final Predicate<Recipe> predicate = RecipePredicateFactory.getPredicate(param, 0);
        final Comparator<Recipe> comparator = RecipeComparatorFactory.getComparator(param);
        final Set<Recipe> recipes = getAllRecipes().stream().collect(Collectors.toSet());
        final IRecipeSelection selection = new RecipeSelection(recipes);

        if(param.equals(Parameter.BY_UNIT)) {
            final List<Map.Entry<Recipe, Double>> m = recipes.stream().map(recipe ->
                Map.entry(
                    recipe, 
                    recipe.getIngredients().stream().map(foodService::getUnits).reduce(0.0, Double::sum)
                )
            ).collect(Collectors.toList());
            return m.stream().sorted((m1, m2) -> Double.compare(m1.getValue(), m2.getValue())).map(Map.Entry<Recipe, Double>::getKey).collect(Collectors.toList());
        }

        return selection.select(predicate).sort(comparator);
    }

    public List<Recipe> getFavoritesRecipes(){
        return List.of();
    }

    // TODO
    public void addFavoriteRecipe(final Recipe recipe) {}

    // TODO
    public List<Recipe> deleteFavoriteRecipe(final String link ){
        return List.of();
    }

    // TODO
    public List<Recipe> getDoneRecipes() {
        return List.of();
    }

    // TODO
    public void addDoneRecipe(final Recipe recipe) {}
}
