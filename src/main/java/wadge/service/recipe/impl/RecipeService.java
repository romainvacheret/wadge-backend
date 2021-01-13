package wadge.service.recipe.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService.RecallType;

@Service
public class RecipeService {
    private final IRecipeDao recipeDao;

    @Autowired
    public RecipeService(@Qualifier("jsonRecipeDao") IRecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public List<Recipe> getRecipesUsingFridge(Map<RecallType, List<String>> products) {
        FridgeSelection select = new FridgeSelection(getAllRecipes(), products);
        
        return select.select().sort();
    }
}
