package wadge.dao.api;

import wadge.model.recipe.Recipe;

import java.util.List;

public interface ISpecificRecipeDao {
	List<Recipe> getFavoritesRecipes();
	List<Recipe> getDoneRecipes();
	void writeFavoriteRecipe(Recipe recipe) ;
	void writeDoneRecipe(Recipe recipe) ;
	void addRecipeFavorite(List<Recipe> recipes);
	void addRecipeDone(List<Recipe> recipes);
	void deletefavoriteRecipe(String link );
	
}
