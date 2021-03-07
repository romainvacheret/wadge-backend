package wadge.dao.impl;

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
public class JsonRecipeDaoTest {
    @Autowired
    private JsonRecipeDao dao;

    @Test
    public void getAllRecipesTest() {
        
        assertTrue(dao.getAllRecipes() instanceof List<?>);
        assertTrue(dao.getAllRecipes().get(0) instanceof Recipe);
    }
}
