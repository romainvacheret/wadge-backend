package wadge.dao.impl;


import static org.junit.Assert.assertTrue;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.recipe.external.MarmitonRecipe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarmitonRecipeDaoTest {
	@Autowired
	private MarmitonRecipeDao marmitonRecipeDao;
	
	
	@Test
	public void getRecipefromUrlTest(){
		assertTrue(marmitonRecipeDao.recipeExternalsFromUrl("tomate") instanceof List<?>);
		assertTrue(marmitonRecipeDao.recipeExternalsFromUrl("tomate").get(0) instanceof  MarmitonRecipe);
	}

	
}
