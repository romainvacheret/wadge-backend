package wadge.food.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import wadge.food.api.Month;
import wadge.food.exceptions.ReadFileFirstException;

public class FoodListTest {
    private FoodList foodList;

    @Before
    public void setUp() {
        this.foodList = FoodList.getInstance();
        
    }

    private void readFile() {
        this.foodList.readFile("food_list.json");
    }

    @Test
    public void testGetFoodFromMonth() {

        if(this.foodList.getFoods() == null) {
            assertThrows(ReadFileFirstException.class, () -> this.foodList.getFoodFromGivenMonth(Month.JANUARY));
        }
        
        this.readFile();
        Month months[] = Month.values();
        Map<Month, List<Food>> results = new HashMap<>();

        List.of(months).stream().forEach(month -> {
            try {
                results.put(month, this.foodList.getFoodFromGivenMonth(month));
            } catch (ReadFileFirstException e) {
                fail("An invalid month was given");
            }
        });

        results.forEach((key, value) -> {
            assertTrue(value.stream().allMatch(food -> 
                food.getAvailability().contains(key.valueOf())));
        });

    }

    @Test
    public void testGetInstance() {
        FoodList l1 = FoodList.getInstance();

        assertTrue(foodList instanceof FoodList);
        assertSame(foodList, l1);
    }

    @Test 
    public void TestGetFoods() {
        FoodList list = FoodList.getInstance();
        
        // assertNull(list.getFoods());
        this.readFile();
        assertTrue(list.getFoods() instanceof List<?>);
        assertTrue(list.getFoods().get(0) instanceof Food);
    }
}