package wadge.model.recipe.external;

import org.junit.Before;
import org.junit.Test;
import wadge.model.recipe.Ingredient;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarmitonRecipeTest {
	private MarmitonRecipe marmitonRecipe;
	@Before
	public void setUp(){marmitonRecipe=new MarmitonRecipe(
			"/link",
			"name",
			List.of("step1","step2"),
			"8",
			"40 min",
			"facile",
			"sur 5 avis",
			"/5",
			"4.8",
			List.of(new Ingredient("ingredint_1","1"), new Ingredient("ingredient_2","2"))
	);
	
}
    @Test
    public void  getNameTest(){
	assertTrue(marmitonRecipe.getName()instanceof String);
	assertEquals(marmitonRecipe.getName(),"name" );
	}
	@Test
	public void getLinkTest(){
		assertEquals(marmitonRecipe.getLink(),"/link");
		assertTrue(marmitonRecipe.getLink() instanceof String);
	}
	@Test
	public void getServingTest(){
		assertTrue(marmitonRecipe.getServings() instanceof  String);
		assertEquals(marmitonRecipe.getServings(),"8");
	}
	@Test
	public void getRatingTest(){
		assertEquals(marmitonRecipe.getRating(),"4.8");
		assertTrue(marmitonRecipe.getRating() instanceof  String);
	}
	@Test
	public void getDifficultyTest(){
		assertEquals(marmitonRecipe.getDifficulty(),"facile");
		assertTrue(marmitonRecipe.getDifficulty() instanceof String);
	}
	@Test
	public void getPreparattionTest(){
		assertEquals(marmitonRecipe.getPreparation(),"40 min");
		assertTrue(marmitonRecipe.getPreparation() instanceof  String);
	}
	@Test
	public void getRatingFractTest(){
		assertTrue(marmitonRecipe.getRatingfract() instanceof  String);
		assertEquals(marmitonRecipe.getRatingfract(),"/5");
	}
	@Test
	public void getOpinionTest(){
		assertEquals(marmitonRecipe.getOpinion(),"sur 5 avis");
		assertTrue(marmitonRecipe.getOpinion() instanceof  String);
	}
	@Test
	public void getStepsTest(){
		assertTrue(marmitonRecipe.getSteps() instanceof List<?>);
		assertTrue(marmitonRecipe.getSteps().get(0)instanceof String);
		assertEquals(marmitonRecipe.getSteps(),List.of("step1","step2"));
	}
	@Test
	public void getIngredientsTest(){
		assertEquals(marmitonRecipe.getIngredients(),List.of(new Ingredient("ingredint_1","1"), new Ingredient("ingredient_2","2")));
		assertTrue(marmitonRecipe.getIngredients().get(0) instanceof Ingredient);
		assertTrue(marmitonRecipe.getIngredients() instanceof  List<?>);
	}
	@Test
	public void toStringTest(){
	String result="MarmitonRecipe{" +
			"link='" + marmitonRecipe.getLink() + '\'' +
			", name='" + marmitonRecipe.getName() + '\'' +
			", ratingfract='" + marmitonRecipe.getRatingfract() + '\'' +
			", opinion='" + marmitonRecipe.getOpinion() + '\'' +
			", steps=" + marmitonRecipe.getSteps() +
			", preparation='" + marmitonRecipe.getPreparation() + '\'' +
			", servings='" + marmitonRecipe.getServings() + '\'' +
			", difficulty='" + marmitonRecipe.getDifficulty() + '\'' +
			", ingredients=" + marmitonRecipe.getIngredients() +
			", rating='" + marmitonRecipe.getRating() + '\'' +
			'}';
	assertEquals(marmitonRecipe.toString(),result);
	}
}
