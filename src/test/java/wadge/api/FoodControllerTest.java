package wadge.api;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.food.Food;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodControllerTest {
    @Autowired
    private FoodController controller;

    @Test
    public void getFridgeListTest() {
        assertTrue(controller.getAllFood() instanceof List<?>);
        assertTrue(controller.getAllFood().get(0) instanceof Food);
    }
}
