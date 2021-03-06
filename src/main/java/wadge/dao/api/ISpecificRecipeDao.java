package wadge.dao.api;

import wadge.model.recipe.Recipe;

import java.util.List;

public interface ISpecificRecipeDao {
	List<Recipe> getRecipes();
	void writeRecipe(Recipe recipe) ;
	void addRecipe(List<Recipe> recipes);
	void deletefromRecipe(String link );
	
}
