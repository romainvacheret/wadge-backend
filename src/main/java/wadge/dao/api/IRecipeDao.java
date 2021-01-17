package wadge.dao.api;

import java.util.List;

import wadge.model.recipe.Recipe;

public interface IRecipeDao {
    List<Recipe> getAllRecipes();
}
