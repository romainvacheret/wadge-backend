package wadge.service.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.fridge.FridgeFood;
import wadge.model.recipe.Ingredient;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataMongoTest
public class FridgeServiceTest {
    @Autowired
    private FridgeService service;

    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllFridge() instanceof List<?>);
        assertTrue(service.getAllFridge().get(0) instanceof FridgeFood);
    }

    @Test
    public void isInFridge() {
        Ingredient ing = new Ingredient();
        ing.setName("poisson");
        ing.setQuantity("2");
        assertEquals("default",service.isInFridge(ing));
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
