package wadge.model.recipe.external;

import org.junit.Before;
import org.junit.Test;
import wadge.model.recipe.Ingredient;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class MarmitonRecipeTest {
	private MarmitonRecipe marmitonRecipe;

	@Before
	public void setUp(){
		marmitonRecipe=new MarmitonRecipe(
			"/link", "name", List.of("step1","step2"), "8", "40 min", "facile","sur 5 avis",
			"/5", "4.8", List.of(new Ingredient("ingredint_1","1"), new Ingredient("ingredient_2","2"))
		);
	}

	@Test
	public void getterSetterTest() {
		assertEquals("name", marmitonRecipe.getName());
		assertEquals("/link", marmitonRecipe.getLink());
		assertEquals("8", marmitonRecipe.getServings());
		assertEquals("4.8", marmitonRecipe.getRating());
		assertEquals("facile", marmitonRecipe.getDifficulty());
		assertEquals("40 min", marmitonRecipe.getPreparation());
		assertEquals("/5", marmitonRecipe.getRatingfract());
		assertEquals("sur 5 avis", marmitonRecipe.getOpinion());
		assertEquals(List.of("step1", "step2"), marmitonRecipe.getSteps());
		assertEquals(List.of(new Ingredient("ingredint_1","1"), new Ingredient("ingredient_2","2")), marmitonRecipe.getIngredients());
		String result = "MarmitonRecipe [difficulty=facile, ingredients=[Ingredient [name=ingredint_1, quantity=1], Ingredient [name=ingredient_2, quantity=2]], link=/link, name=name, opinion=sur 5 avis, preparation=40 min, rating=4.8, ratingfract=/5, servings=8, steps=[step1, step2]]";
		assertEquals(result, marmitonRecipe.toString());
	}
}
