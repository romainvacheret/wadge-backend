package wadge.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import wadge.dao.api.ISpecificRecipeDao;
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
@Repository("jsonDoneRecipeDao")
public class JsonDoneRecipeDao implements ISpecificRecipeDao {
	private final ObjectMapper mapper;
	private final Map<String, Recipe> donerecipes;
	static final String FILE_DONE_RECIPE="done_recipe.json";
	private static Logger logger = Logger.getLogger(JsonRecipeDao.class.getName());
	
	public JsonDoneRecipeDao(){
		mapper=new ObjectMapper();
		donerecipes=new HashMap<>();
		try {
			List<Recipe> listf= Arrays.asList(mapper.readValue(Paths.get(FILE_DONE_RECIPE).toFile(),Recipe.class));
			addRecipe(listf);
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	@Override
	public void addRecipe(List<Recipe> recipes){
		recipes.stream().forEach(recipe -> donerecipes.put(recipe.getLink(),recipe));
	}
	@Override
	public List<Recipe> getRecipes() {
		return donerecipes.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public void writeRecipe(Recipe recipe) {
		donerecipes.put(recipe.getLink(),recipe);
		try {
			mapper.writeValue(Paths.get(FILE_DONE_RECIPE).toFile(),donerecipes.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
	
	@Override
	public void deletefromRecipe(String link) {
		donerecipes.remove(link);
		try {
			mapper.writeValue(Paths.get(FILE_DONE_RECIPE).toFile(),donerecipes.values());
		}
		catch (IOException e){logger.log(Level.FINE, e.getMessage(), e);}
	}
}
