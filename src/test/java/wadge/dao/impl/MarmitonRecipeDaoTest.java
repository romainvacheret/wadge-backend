package wadge.dao.impl;


import org.junit.Before;
import org.junit.Test;
import wadge.model.recipe.external.MarmitonRecipe;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MarmitonRecipeDaoTest {
	private MarmitonRecipeDao marmitonRecipeDao;
	
	@Before
	public void setUp(){
		marmitonRecipeDao=new MarmitonRecipeDao();
	}
	@Test
	public void getRecipefromUrlTest(){
		assertTrue(marmitonRecipeDao.recipeExternalsFromUrl("tomate") instanceof List<?>);
		assertTrue(marmitonRecipeDao.recipeExternalsFromUrl("tomate").get(0) instanceof  MarmitonRecipe);
	}
	
}
