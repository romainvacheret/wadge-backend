package wadge.api;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import wadge.dao.api.IFridgeDao;
import wadge.dao.impl.JsonFridgeDao;
import wadge.model.fridge.FridgeFood;
import wadge.service.fridge.FridgeService;


public class FridgeControllerTest {
    private FridgeController controller;

    @Before
    public void setUp() {
        IFridgeDao dao = new JsonFridgeDao();
        FridgeService service = new FridgeService(dao);
        controller = new FridgeController(service);
    }

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
