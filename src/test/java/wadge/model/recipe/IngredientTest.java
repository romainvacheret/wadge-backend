package wadge.model.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import wadge.model.recipe.Ingredient.Unit;

public class IngredientTest {
    private Ingredient ingredient;
    private Ingredient i1;
    private Ingredient i2;

    @Before
    public void setUp() {
        ingredient = new Ingredient("Ingredient", "42");
        i1 = new Ingredient("kg de tomate", "2");
        i2 = new Ingredient("g de mache", "43");
    }

    @Test
    public void getterSetterTest() {
        String result = "Ingredient [name=Ingredient, quantity=42]";
        Ingredient i1 = new Ingredient("Ingredient", "42");
        Ingredient i2 = new Ingredient("Ingredient", "43");
        Ingredient i3 = new Ingredient("Ingredient2", "42");

        assertEquals("Ingredient", ingredient.getName());
        assertEquals("42", ingredient.getQuantity());
        assertEquals(result, ingredient.toString());
        assertEquals(ingredient, i1);
        assertEquals(ingredient, i2);
        assertNotEquals(ingredient, i3);

        ingredient.setQuantity("45");
        assertEquals("45", ingredient.getQuantity());
        
        ingredient.setName("Ingredient2");
        assertEquals("Ingredient2", ingredient.getName());
        
    }

    @Test
    public void extractNameTest() {
        assertEquals("Ingredient", Ingredient.extractName(ingredient));
        assertEquals("tomate", Ingredient.extractName(i1));
        assertEquals("mache", Ingredient.extractName(i2));
    }

    @Test
    public void getUnitTest() {
        assertEquals(Unit.NONE, Ingredient.getUnit(ingredient.getName()));
        assertEquals(Unit.KG, Ingredient.getUnit(i1.getName()));
        assertEquals(Unit.G, Ingredient.getUnit(i2.getName()));
    }
}
