package wadge.model.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FoodTest {
    private Food food;

    @Before
    public void setUp() {
        food = new Food("Name", "Type", new String[] {"a", "b"}, 42);
    }

    @Test
    public void getNameTest() {
        assertTrue(food.getName() instanceof String);
        assertEquals("Name", food.getName());
    }

    @Test
    public void getTypeTest() {
        assertTrue(food.getType() instanceof String);
        assertEquals("Type", food.getType());
    }

    @Test
    public void getAvailabilityTest() {
        assertTrue(food.getAvailability() instanceof List<?>);
        assertTrue(food.getAvailability().get(0) instanceof String);
        assertEquals(List.of("a", "b"), food.getAvailability());
    }

    @Test
    public void getDaysTest() {
        assertTrue(Integer.valueOf(food.getDays()) instanceof Integer);
        assertEquals(42, food.getDays());
    }

    @Test
    public void toSringTest() {
        String result = "Food [availability=[a, b], days=42, name=Name, type=Type]";
        assertEquals(result, food.toString());
    }
}
