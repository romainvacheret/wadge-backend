package wadge.service.recipe.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeDao;
import wadge.dao.api.IExternalRecipeDao;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.service.fridge.FridgeService.RecallType;

@Service
public class RecipeService {
    private final IRecipeDao recipeDao;
    private final IExternalRecipeDao marmitonDao;

    @Autowired
    public RecipeService(@Qualifier("jsonRecipeDao") IRecipeDao recipeDao, @Qualifier("jsonRecipeExtDao") IExternalRecipeDao recipeExternalDao) {
        this.recipeDao = recipeDao;
        this.marmitonDao = recipeExternalDao;
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public List<Recipe> getRecipesUsingFridge(Map<RecallType, List<String>> products) {
        FridgeSelection select = new FridgeSelection(getAllRecipes(), products);
        
        return select.select().sort();
    }

    public List<Recipe> getRecipesUsingUserList(List<String> userSelection){
        UserListSelection userSelect = new UserListSelection(getAllRecipes(), userSelection);
        return userSelect.select().sort();
    }

    public List<Recipe> writeRecipe(String searchquery){
		List<MarmitonRecipe> x = marmitonDao.recipeExternalsFromUrl(searchquery);
		System.out.println(marmitonDao.toRecipe(x));
        recipeDao.addAllRecipes(marmitonDao.toRecipe(x));
		return recipeDao.getAllRecipes();
		
	}
}
