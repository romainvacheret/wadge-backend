package wadge.model.fridge;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class FridgeFoodBuilderTest {
    private FridgeFoodBuilder builder;
    private UUID id = UUID.randomUUID();
    private UUID foodElementId = UUID.randomUUID();
    private FoodElement foodElement; 


    @Before
    public void setUp() {
        builder = new FridgeFoodBuilder();
        foodElement = new FoodElement(foodElementId, "date 1", "date 2", 42);
        builder.withId(id).withName("tomate").withProducts(List.of(foodElement));
    }   

    @Test
    public void createFridgeFoodTest() {
        FridgeFood food = builder.createFridgeFood();
        assertEquals("tomate", food.getName());
        assertEquals(List.of(foodElement), food.getProducts());
        assertEquals(Map.of(foodElementId, foodElement), food.getProducts2());
        assertEquals(id, food.getId());
    } 
}
