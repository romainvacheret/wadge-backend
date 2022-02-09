package wadge.service.fridge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.dao.FridgeFoodRepository;
import wadge.model.recipe.Ingredient;
import wadge.service.food.FoodService;
import wadge.utils.db.SequenceGenerator;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class FridgeServiceTest {
    @MockBean
    private FoodService foodService;
    @MockBean
    private FridgeFoodRepository fridgeFoodRepository;
    @Mock
    private SequenceGenerator sequenceGenerator;
    private FridgeService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FridgeService(
            foodService,
            fridgeFoodRepository,
            sequenceGenerator);
    }

    @Test
    void getAllFridge() {
        underTest.getAllFridge();
        verify(fridgeFoodRepository).findAll();
    }

    @Test
    void isInFridge() {
        Ingredient ing = new Ingredient();
        ing.setName("poisson");
        ing.setQuantity("2");
        assertEquals("default",underTest.isInFridge(ing));
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
