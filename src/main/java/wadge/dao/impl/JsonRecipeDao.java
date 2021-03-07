package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
<<<<<<< HEAD


=======
>>>>>>> 1a40a11248b5dac4e21389bd5ec723a3c1230602
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;

@Repository("jsonRecipeDao")
public class JsonRecipeDao implements IRecipeDao {
        private final List<Recipe> recipes;
        private final ObjectMapper mapper;
        static final String FILE_NAME = "recipe_list.json";
        private static Logger logger = Logger.getLogger(JsonRecipeDao.class.getName());

    public JsonRecipeDao() {
        mapper = new ObjectMapper();
        recipes = new ArrayList<>();

        try {
              recipes.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), Recipe[].class)));
          
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
<<<<<<< HEAD
   
=======
>>>>>>> 1a40a11248b5dac4e21389bd5ec723a3c1230602
    

}
