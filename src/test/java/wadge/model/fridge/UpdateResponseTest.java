package wadge.model.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class UpdateResponseTest {
    private UpdateResponse updateResponse; 
    private UUID id;

    @Before
    public void setUp() {
        id = UUID.randomUUID();
        updateResponse = new UpdateResponse(id, "tomate", 3);
    }

    @Test
    public void getterSetterTest() {
        UpdateResponse u1 = new UpdateResponse(UUID.randomUUID(), "tomate", 3);
        UpdateResponse u2 = new UpdateResponse(id, "tomate", 3);
        UpdateResponse u3 = new UpdateResponse(UUID.randomUUID(), "orange", 5);
        assertEquals(id, updateResponse.getId());
        assertEquals("tomate", updateResponse.getFridgeFood());
        assertEquals(3, updateResponse.getQuantity());
        assertNotEquals(u1, updateResponse);
        assertNotEquals(u3, updateResponse);
        assertNotEquals(u2,updateResponse);
    }
}
