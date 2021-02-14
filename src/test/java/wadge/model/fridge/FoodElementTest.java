package wadge.model.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

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
    public void getterSetterTest() {
        String result = String.format("FoodElement [id=%s, insertionDate=date 1, peremptionDate=date 2, quantity=42]", id);
        FoodElement e1 = new FoodElement(id, "date 1", "date 2", 42);
        assertEquals(42, element.getQuantity());
        assertTrue(Integer.valueOf(element.getQuantity()) instanceof Integer);
        assertEquals("date 1", element.getInsertionDate());
        assertEquals("date 2", element.getPeremptionDate());
        assertEquals(result, element.toString());
        assertEquals(e1, element);
    } 
}
