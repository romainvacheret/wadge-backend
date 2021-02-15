package wadge.model.recipe;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecipeTest {
    private Recipe recipe;

    @Before
    public void setUp() {
        recipe = new Recipe(
            "Name", 
            List.of("Step 1", "Step 2", "Step 3"), 
            4, 
            45, 
            2,
            4.2, 
            "myLink.com", 
            List.of(new Ingredient("Ingredient", "12"))
        );
    }

    @Test
    public void getterSetter() {

        String result = "Recipe [difficulty=2, ingredients=[Ingredient [name=Ingredient, quantity=12]], link=myLink.com, name=Name, preparation=45, rating=4.2, servings=4, steps=[Step 1, Step 2, Step 3]]";
        assertEquals("Name", recipe.getName());
        assertEquals(List.of("Step 1", "Step 2", "Step 3"), recipe.getSteps());
        assertEquals(4, recipe.getServings());
        assertEquals(45, recipe.getPreparation());
        assertEquals(2, recipe.getDifficulty());
        assertEquals(4.2, recipe.getRating(), 0.0);
        assertEquals("myLink.com", recipe.getLink());
        assertEquals(List.of(new Ingredient("Ingredient", "12")), recipe.getIngredients());
        assertEquals(result, recipe.toString());
    }
}
