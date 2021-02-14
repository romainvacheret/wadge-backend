package wadge.model.recipe;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IngredientTest {
    private Ingredient ingredient;

    @Before
    public void setUp() {
        ingredient = new Ingredient("Ingredient", "42");
    }

    @Test
    public void getterSetterTest() {
        String result = "Ingredient [name=Ingredient, quantity=42]";
        assertEquals("Ingredient", ingredient.getName());
        assertEquals("42", ingredient.getQuantity());
        assertEquals(result, ingredient.toString());
    }
}
