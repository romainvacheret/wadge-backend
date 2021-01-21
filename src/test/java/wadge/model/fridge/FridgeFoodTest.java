package wadge.model.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class FridgeFoodTest {
    private FridgeFood food;
    private UUID id;

    @Before
    public void setUp() {
        id = UUID.randomUUID();
        food = new FridgeFood(id, "Food", new FoodElement[] {new FoodElement("date 1", "date 2", 42)});
    }

    @Test
    public void getNameTest() {
        assertTrue(food.getName() instanceof String);
        assertEquals("Food", food.getName());
    }

    @Test
    public void getProductsTest() {
        assertTrue(food.getProducts() instanceof List<?>);
        assertTrue(food.getProducts().get(0) instanceof FoodElement);
        assertEquals(new FoodElement("date 1", "date 2", 42), food.getProducts().get(0));
    }

    @Test
    public void toStringTest() {
        System.out.println(food.toString());
        String result = String.format("FridgeFood [id=%s, name=Food, products=[FoodElement [insertionDate=date 1, peremptionDate=date 2, quantity=42]]]", id);
        System.out.println(result);
        System.out.println(food.toString());
        assertEquals(result, food.toString());
    }

    @Test
    public void equalsTest() {
        FridgeFood f1 = new FridgeFood("Food", List.of(new FoodElement("date 1", "date 2", 42)));
        FridgeFood f2 = new FridgeFood("Name", List.of(new FoodElement("d1", "d2", 42)));
        assertEquals(f1, food);
        assertNotEquals(null, food);
        assertNotEquals(f2, food);
    }
}
