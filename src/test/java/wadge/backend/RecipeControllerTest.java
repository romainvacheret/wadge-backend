package wadge.backend;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.recipe.impl.Recipe;

public class RecipeControllerTest {
    private RecipeController controller;

    @Before
    public void setUp() {
        controller = new RecipeController();
    }

    @Test
    public void getRecipesTest() {
        assertTrue(controller.getRecipes() instanceof List<?>);
        assertTrue(controller.getRecipes().get(0) instanceof Recipe);
    }
}
