package wadge.api;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.dao.api.IFoodDao;
import wadge.dao.impl.JsonFoodDao;
import wadge.model.food.Food;
import wadge.service.food.FoodService;

public class FoodControllerTest {
    private FoodController controller;

    @Before
    public void setUp() {
        IFoodDao dao = new JsonFoodDao();
        FoodService service = new FoodService(dao);
        controller = new FoodController(service);
    }

    @Test
    public void getFridgeListTest() {
        assertTrue(controller.getAllFood() instanceof List<?>);
        assertTrue(controller.getAllFood().get(0) instanceof Food);
    }
}
