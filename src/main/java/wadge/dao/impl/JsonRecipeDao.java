package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;

@Repository("jsonRecipeDao")
public class JsonRecipeDao implements IRecipeDao {
        private final List<Recipe> recipes;
        private final ObjectMapper mapper;
        private static final String FILE_NAME = "recipe_list.json";

    public JsonRecipeDao() {
        mapper = new ObjectMapper();
        recipes = new ArrayList<>();

        try {
            recipes.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), Recipe[].class)));
        } catch (IOException e) {
            // logger.log(Level.FINE, e.getMessage(), e);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }
}
