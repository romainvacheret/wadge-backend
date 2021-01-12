package wadge.model.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IngredientTest {
    private Ingredient ingredient;

    @Before
    public void setUp() {
        ingredient = new Ingredient("Ingredient", "42");
    }

    @Test
    public void getNameTest() {
        assertTrue(ingredient.getName() instanceof String);
        assertEquals("Ingredient", ingredient.getName());
    }

    @Test
    public void getQuantityTest() {
        assertTrue(Integer.valueOf(ingredient.getQuantity()) instanceof Integer);
        assertEquals("42", ingredient.getQuantity());
    }

    public void toStringTest() {
        String result = "Ingredient [name=Ingredient, quantity=42]";
        assertEquals(result, ingredient.toString());
    }
}
