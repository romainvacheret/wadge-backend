package wadge.service.recipe.impl;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import wadge.dao.RecipeRepository;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.RecipeTag;
import wadge.service.food.FoodService;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.api.IRecipeSelection;
import wadge.service.recipe.impl.RecipeSelection.Parameter;
import wadge.utils.db.SequenceGenerator;


@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final FridgeService fridgeService;
    private final FoodService foodService;

    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }

    final ToIntFunction<Recipe> recipeScoring = recipe -> recipe.getIngredients().stream()
        .map(SelectionWithFridge.ingredientScoring)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(0, Integer::sum);

    public List<Recipe> getRecipesUsingFridge() {
        return new SelectionWithFridge(new HashSet<>(getAllRecipes()), fridgeService)
            .compute(recipeScoring)
            .filter(x -> x > 0)
            .sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
    }

    public Map<String, String> getRecipeIngredient(final Recipe recipe) {
        return recipe.getIngredients().stream().collect(Collectors.toMap(
            Ingredient::getName,
            fridgeService::isInFridge));
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
            ).toList();

            return m.stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
        }

        return selection.select(predicate).sort(comparator);
    }

    // ---- Tagged recipes ----

    public List<Recipe> getTaggedRecipes(final RecipeTag tag) {
        return repository.findAll().stream()
            .filter(recipe -> recipe.getTags().contains(tag))
            .toList();
    }

    public void addTagToRecipe(final long id, final RecipeTag tag) {
        repository.findById(id).ifPresent(recipe -> {
            recipe.getTags().add(tag);
            repository.save(recipe);
        });
    }

    public void removeTagToRecipe(final long id, final RecipeTag tag) {
        repository.findById(id).ifPresent(recipe -> {
            recipe.getTags().remove(tag);
            repository.save(recipe);
        });
    }
}
