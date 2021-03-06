package wadge.dao.impl;

import org.junit.Before;
import org.junit.Test;
import wadge.model.recipe.Recipe;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JsonDoneRecipeDaoTest {
	
	private JsonDoneRecipeDao dao;
	
	@Before
	public void setUp() {
		dao = new JsonDoneRecipeDao();
	}
	
	@Test
	public void getAllRecipesTest() {
		
		assertTrue(dao.getRecipes() instanceof List<?>);
		assertTrue(dao.getRecipes().get(0) instanceof Recipe);
	}
}
