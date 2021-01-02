package wadge.backend;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.food.impl.Food;

public class FoodControllerTest {
    private FoodController controller;

    @Before
    public void setUp() {
        controller = new FoodController();
    }

    @Test
    public void getFridgeListTest() {
        assertTrue(controller.getFridgeList() instanceof List<?>);
        assertTrue(controller.getFridgeList().get(0) instanceof Food);
    }
}
