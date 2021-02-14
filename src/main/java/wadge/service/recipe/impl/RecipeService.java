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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IExternalRecipeDao;
import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.api.AbstractRecipeSelection;
import wadge.service.recipe.api.IRecipeSelection;
import wadge.service.recipe.impl.RecipeSelection.Parameter;

@Service
public class RecipeService {
    private final IRecipeDao recipeDao;
    private final FridgeService fridgeService;
    private final IExternalRecipeDao marmitonDao;

    @Autowired
    public RecipeService(@Qualifier("jsonRecipeDao") IRecipeDao recipeDao, FridgeService fridgeService, @Qualifier("jsonRecipeExtDao") IExternalRecipeDao recipeExternalDao) {
        this.recipeDao = recipeDao;
        this.fridgeService = fridgeService;
        this.marmitonDao = recipeExternalDao;
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    ToIntFunction<Recipe> recipeScoring = recipe -> recipe.getIngredients().stream().map(SelectionWithFridge.ingredientScoring)
            .filter(Optional::isPresent).map(Optional::get).reduce(0, (a, b) -> a + b);

    public List<Recipe> getRecipesUsingFridge() {
        AbstractRecipeSelection sel = new SelectionWithFridge(getAllRecipes().stream().collect(Collectors.toSet()), fridgeService);
        return sel.compute(recipeScoring).filter(x -> x > 0).sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
    }

    public List<Recipe> getRecipesFromMarmiton(String query){
		List<MarmitonRecipe> x = marmitonDao.recipeExternalsFromUrl(query);
        recipeDao.addAllRecipes(marmitonDao.toRecipe(x));
        System.out.println(x.stream().map(MarmitonRecipe::getDifficulty).collect(Collectors.toList()));
		return recipeDao.getAllRecipes();
		
	}

    public List<Recipe> selectRecipes(Parameter param) {
        if(param.equals(Parameter.USING_FRIDGE)) {
            return getRecipesUsingFridge(); 
        } else if(param.equals(Parameter.EVERYTHING)) {
            return getAllRecipes();
        }

        Predicate<Recipe> predicate = RecipePredicateFactory.getPredicate(param, 0); 
        Comparator<Recipe> comparator = RecipeComparatorFactory.getComparator(param);

        Set<Recipe> recipes = recipeDao.getAllRecipes().stream().collect(Collectors.toSet());
        IRecipeSelection selection = new RecipeSelection(recipes);

        return selection.select(predicate).sort(comparator);
    }
}
