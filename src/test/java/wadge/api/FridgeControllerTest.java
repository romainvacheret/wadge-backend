package wadge.api;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.fridge.FridgeFood;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FridgeControllerTest {
    @Autowired
    private FridgeController controller;

    @Test
    public void getFridge() {
        assertTrue(controller.getAllFridge() instanceof List<?>);
        assertTrue(controller.getAllFridge().get(0) instanceof FridgeFood);
    }

    @Test
    public void getExpirationAlertsTest() {
        assertTrue(controller.getExpirationAlerts() instanceof Map<?, ?>);
    }
}
