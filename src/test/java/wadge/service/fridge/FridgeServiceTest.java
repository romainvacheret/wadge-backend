package wadge.service.fridge;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.dao.api.IFridgeDao;
import wadge.dao.impl.JsonFridgeDao;
import wadge.model.fridge.FridgeFood;

public class FridgeServiceTest {
    private FridgeService service;

    @Before
    public void setUp() {
        IFridgeDao dao = new JsonFridgeDao();
        service = new FridgeService(dao);
    }

    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllFridge() instanceof List<?>);
        assertTrue(service.getAllFridge().get(0) instanceof FridgeFood);
    }

    @Test
    public void addAllToFridge() {
        fail("TODO");
        //TODO
    }

    @Test
    public void getExpirationList() {
        fail("TODO");
        //TODO
    }
}
