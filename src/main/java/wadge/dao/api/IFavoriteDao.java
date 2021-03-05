package wadge.dao.api;

import wadge.model.recipe.Recipe;

import java.util.List;

public interface IFavoriteDao {
	List<Recipe> getFavorites();
	void writeFavorieRecipe(Recipe recipe) ;
	void addFavories(List<Recipe> recipes);
	void deleteFavorite(String link );
}
