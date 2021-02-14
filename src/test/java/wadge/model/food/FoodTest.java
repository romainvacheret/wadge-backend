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
        food = new Food("Name", "Type", new Month[] {Month.JANUARY, Month.FEBRUARY}, 42);
    }

    @Test
    public void getterSetterTest() {
        String result = "Food [availability=[JANUARY, FEBRUARY], days=42, name=Name, type=Type]";
        assertEquals("Name", food.getName());
        assertTrue(food.getType() instanceof String);
        assertEquals(42, food.getDays());
        assertEquals(List.of(Month.JANUARY, Month.FEBRUARY), food.getAvailability());
        assertEquals(result, food.toString());
    }
}
