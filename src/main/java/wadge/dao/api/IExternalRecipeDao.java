package wadge.dao.api;

import java.util.List;

import wadge.model.recipe.Recipe;
import wadge.model.recipe.external.MarmitonRecipe;

public interface IExternalRecipeDao {
	void writeRecipeExternal();
	List<MarmitonRecipe> recipeExternalsFromUrl(String search);
	List<Recipe> toRecipe(List<MarmitonRecipe> recipes);
	
	
}
