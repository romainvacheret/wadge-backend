package wadge.api;

import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wadge.service.shopping.ShoppingService;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class ShoppingControllerTest {
    @MockBean
    private ShoppingService shoppingService;
    private ShoppingController underTest;

    @BeforeEach
    void setUp() {
        underTest = new ShoppingController(shoppingService);
    }

    @Test
    void getShoppingListTest() {
        underTest.getShoppingList();
        verify(shoppingService).getShoppingList();
    }
}
