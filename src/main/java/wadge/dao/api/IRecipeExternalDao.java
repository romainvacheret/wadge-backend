package wadge.dao.api;

import java.util.List;

import wadge.model.recipe.Recipe;
import wadge.model.recipeExternal.RecipeExternal;

public interface IRecipeExternalDao {
	void writeRecipeExternal();
	List<RecipeExternal> recipeExternalsFromUrl(String search);
	List<Recipe> toRecipe(List<RecipeExternal> recipes);
	
}
