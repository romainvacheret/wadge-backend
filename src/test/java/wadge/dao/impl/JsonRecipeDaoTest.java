package wadge.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.model.recipe.Recipe;

public class JsonRecipeDaoTest {
    private JsonRecipeDao dao;

    @Before
    public void setUp() {
        dao = new JsonRecipeDao();
    }

    @Test
    public void getAllRecipesTest() {
        
        assertTrue(dao.getAllRecipes() instanceof List<?>);
        assertTrue(dao.getAllRecipes().get(0) instanceof Recipe);
    }
}
