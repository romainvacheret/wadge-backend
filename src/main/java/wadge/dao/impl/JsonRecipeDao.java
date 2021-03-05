package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;

@Repository("jsonRecipeDao")
public class JsonRecipeDao implements IRecipeDao {
        private final List<Recipe> recipes;
        private final  Map<String,Recipe> favorites;
        private final ObjectMapper mapper;
        static final String FILE_NAME = "recipe_list.json";
        static final String FILE_FAVORITE="favorites.json";
        private static Logger logger = Logger.getLogger(JsonRecipeDao.class.getName());

    public JsonRecipeDao() {
        mapper = new ObjectMapper();
        recipes = new ArrayList<>();
        favorites=new HashMap<>();

        try {
              recipes.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), Recipe[].class)));
           List<Recipe> listf= Arrays.asList(mapper.readValue(Paths.get(FILE_FAVORITE).toFile(),Recipe.class));
           addFavories(listf);
        } catch (IOException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public List<Recipe> addAllRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
        return this.recipes;
    }
    public void addFavories(List<Recipe> recipes){
        recipes.stream().forEach(recipe -> favorites.put(recipe.getLink(),recipe));
    }
   public List<Recipe> getFavorites(){
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
    

}
