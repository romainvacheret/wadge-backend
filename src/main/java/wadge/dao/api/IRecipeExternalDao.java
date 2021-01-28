package wadge.dao.api;

import wadge.model.recipeExternal.RecipeExternal;

import java.util.List;

public interface IRecipeExternalDao {
	void writeRecipeExternal(String search);
	void recipeExternalsFromUrl(String search);
	List<RecipeExternal> readExternalRecipe();
	
}
