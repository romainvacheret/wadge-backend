package wadge.dao.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonSpecificRecipeDaoTest {
	
	private JsonSepecificRecipeDao jsonDao;
	
	@Before
	public void setUp() {
		jsonDao = new JsonSepecificRecipeDao();
	}
	
	@Test
	public void getDoneRecipesTest() {
		assertTrue(jsonDao.getDoneRecipes() instanceof List<?>);
		assertNotNull(jsonDao.getDoneRecipes());
	}
	@Test
	public void getFavoritesRecipesTest(){
		assertTrue(jsonDao.getFavoritesRecipes() instanceof List<?>);
		assertNotNull(jsonDao.getFavoritesRecipes());
	}
}
