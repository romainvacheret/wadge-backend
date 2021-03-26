package wadge.service.recipe.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.dao.api.IRecipeDao;
import wadge.dao.impl.JsonRecipeDao;
import wadge.model.recipe.Recipe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTest {
    @Autowired
    private RecipeService service;
    
    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllRecipes() instanceof List<?>);
        assertTrue(service.getAllRecipes().get(0) instanceof Recipe);
    }
}
