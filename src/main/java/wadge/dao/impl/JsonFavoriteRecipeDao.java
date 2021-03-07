package wadge.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import wadge.dao.api.ISpecificRecipeDao;
import wadge.model.recipe.Recipe;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository("jsonFavoriteRecipeDao")

public class JsonFavoriteRecipeDao implements ISpecificRecipeDao {
	private final ObjectMapper mapper;
	private final Map<String,Recipe> favorites;
	static final String FILE_FAVORITE="favorites.json";
	private static Logger logger = Logger.getLogger(JsonRecipeDao.class.getName());
	private final List<Recipe> recipes;
	public JsonFavoriteRecipeDao(){
		mapper=new ObjectMapper();
		favorites=new HashMap<>();
		recipes = new ArrayList<>();
		
		try {
			recipes.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_FAVORITE).toFile(), Recipe[].class)));
			addRecipe(recipes);
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	@Override
	public void addRecipe(List<Recipe> recipes){
		recipes.stream().forEach(recipe -> favorites.put(recipe.getLink(),recipe));
	}
	@Override
	public List<Recipe> getRecipes() {
		return favorites.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public void writeRecipe(Recipe recipe) {
		favorites.put(recipe.getLink(),recipe);
		try {
			mapper.writeValue(Paths.get(FILE_FAVORITE).toFile(),favorites.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
	
	@Override
	public void deletefromRecipe(String link) {
		favorites.remove(link);
		try {
			mapper.writeValue(Paths.get(FILE_FAVORITE).toFile(),favorites.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
}

