package wadge.dao.impl;

import org.junit.Before;
import org.junit.Test;
import wadge.model.recipe.Recipe;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JsonSpecificRecipeDaoTest {
	
	private JsonSepecificRecipeDao dao;
	
	@Before
	public void setUp() {
		dao = new JsonSepecificRecipeDao();
	}
	
	@Test
	public void getAllRecipesTest() {
		
		assertTrue(dao.getFavoritesRecipes() instanceof List<?>);
		assertTrue(dao.getDoneRecipes() instanceof List<?>);
		assertTrue(dao.getFavoritesRecipes().get(0) instanceof Recipe);
		assertTrue(dao.getDoneRecipes().get(0) instanceof Recipe);
		

	}
}
