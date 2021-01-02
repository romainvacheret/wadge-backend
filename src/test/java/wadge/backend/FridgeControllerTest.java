package wadge.backend;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import wadge.fridge.impl.FridgeFood;

public class FridgeControllerTest {
    private FridgeController controller;

    @Before
    public void setUp() {
        controller = new FridgeController();
    }

    @Test
    public void getFridge() {
        assertTrue(controller.getFridge() instanceof List<?>);
        assertTrue(controller.getFridge().get(0) instanceof FridgeFood);
    }

    @Test
    public void getExpirationAlertsTest() {
        assertTrue(controller.getExpirationAlerts() instanceof Map<?, ?>);
    }
    
}
