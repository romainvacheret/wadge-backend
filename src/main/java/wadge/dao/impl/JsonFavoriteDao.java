package wadge.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import wadge.dao.api.IFavoriteDao;
import wadge.model.recipe.Recipe;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository("jsonFavoriteDao")

public class JsonFavoriteDao implements IFavoriteDao {
	private final ObjectMapper mapper;
	private final Map<String,Recipe> favorites;
	static final String FILE_FAVORITE="favorites.json";
	private static Logger logger = Logger.getLogger(JsonRecipeDao.class.getName());
	
	public JsonFavoriteDao(){
		mapper=new ObjectMapper();
		favorites=new HashMap<>();
		try {
			List<Recipe> listf= Arrays.asList(mapper.readValue(Paths.get(FILE_FAVORITE).toFile(),Recipe.class));
			addFavories(listf);
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	@Override
	public void addFavories(List<Recipe> recipes){
		recipes.stream().forEach(recipe -> favorites.put(recipe.getLink(),recipe));
	}
	@Override
	public List<Recipe> getFavorites() {
		return favorites.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public void writeFavorieRecipe(Recipe recipe) {
		favorites.put(recipe.getLink(),recipe);
		try {
			mapper.writeValue(Paths.get(FILE_FAVORITE).toFile(),favorites.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
	
	@Override
	public void deleteFavorite(String link) {
		favorites.remove(link);
		try {
			mapper.writeValue(Paths.get(FILE_FAVORITE).toFile(),favorites.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
}

