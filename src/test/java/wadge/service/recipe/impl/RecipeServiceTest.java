package wadge.service.recipe.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.dao.api.IRecipeDao;
import wadge.dao.impl.JsonRecipeDao;
import wadge.model.recipe.Recipe;

public class RecipeServiceTest {
    private RecipeService service;

    @Before
    public void setUp() {
        IRecipeDao dao = new JsonRecipeDao();
        service = new RecipeService(dao, null, null,null);
    }

    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllRecipes() instanceof List<?>);
        assertTrue(service.getAllRecipes().get(0) instanceof Recipe);
    }
}
