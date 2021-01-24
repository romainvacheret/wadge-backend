package wadge.model.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class FoodElementTest {
    private FoodElement element;
    private UUID id;

    @Before
    public void setUp() {
        id = UUID.randomUUID();
        element = new FoodElement(id, "date 1", "date 2", 42);
    }

    @Test
    public void getInsertionDateTest() {
        assertTrue(element.getInsertionDate() instanceof String);
        assertEquals("date 1", element.getInsertionDate());
    }

    @Test
    public void getPremptionDateTest() {
        assertTrue(element.getPeremptionDate() instanceof String);
        assertEquals("date 2", element.getPeremptionDate());
    }

    @Test
    public void getQuantityTest() {
        assertTrue(Integer.valueOf(element.getQuantity()) instanceof Integer);
        assertEquals(42, element.getQuantity());
    }

    @Test
    public void toStringTest() {
        String result = String.format("FoodElement [id=%s, insertionDate=date 1, peremptionDate=date 2, quantity=42]", id);
        assertEquals(result, element.toString());
    }

    @Test
    public void equalsTest() {
        FoodElement e1 = new FoodElement(id, "date 1", "date 2", 42);
        assertEquals(e1, element);
        assertNotEquals(null, element);
    }
}
