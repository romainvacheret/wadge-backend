package wadge.api;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataMongoTest
public class ShoppingControllerTest {
    @Autowired
    private ShoppingController controller;

    @Test
    public void getShoppinListTest() {
        assertTrue(controller.getShoppingList() instanceof Set<?>);
    }
}
