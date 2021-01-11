package wadge.service.recipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;

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
}
