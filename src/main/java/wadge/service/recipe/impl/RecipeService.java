package wadge.service.recipe.impl;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeDao;
import wadge.dao.api.IExternalRecipeDao;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.api.AbstractRecipeSelection;

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

    public List<Recipe> getRecipesUsingUserList(List<String> userSelection){
        UserListSelection userSelect = new UserListSelection(getAllRecipes(), userSelection);
        return userSelect.select().sort();
    }

    public List<Recipe> getRecipesFromMarmiton(String query){
		List<MarmitonRecipe> x = marmitonDao.recipeExternalsFromUrl(query);
        recipeDao.addAllRecipes(marmitonDao.toRecipe(x));
		return recipeDao.getAllRecipes();
		
	}

    public Map<String, String> getRecipeIngredient(Recipe recipe) {
        List<Ingredient> list = recipe.getIngredients();
        Map<String, String> isPresentList = new HashMap<>();
        list.forEach(food -> {
            isPresentList.put(food.getName(),fridgeService.isInFridge(food));
        });
        return isPresentList;
    }
}
