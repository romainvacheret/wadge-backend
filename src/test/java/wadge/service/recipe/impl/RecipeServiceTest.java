package wadge.service.recipe.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.recipe.Recipe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTest {
    @Autowired
    private RecipeService service;

<<<<<<< HEAD
    @Before
    public void setUp() {
        IRecipeDao dao = new JsonRecipeDao();
        service = new RecipeService(dao, null, null,null,null);
    }

=======
>>>>>>> 1a40a11248b5dac4e21389bd5ec723a3c1230602
    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllRecipes() instanceof List<?>);
        assertTrue(service.getAllRecipes().get(0) instanceof Recipe);
    }
}
