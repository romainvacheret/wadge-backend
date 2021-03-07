package wadge.service.fridge;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.fridge.FridgeFood;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FridgeServiceTest {
    @Autowired
    private FridgeService service;

    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllFridge() instanceof List<?>);
        assertTrue(service.getAllFridge().get(0) instanceof FridgeFood);
    }

    // @Test
    // public void addAllToFridge() {
    //     fail("TODO");
    //     //TODO
    // }

    // @Test
    // public void getExpirationList() {
    //     fail("TODO");
    //     //TODO
    // }
}
