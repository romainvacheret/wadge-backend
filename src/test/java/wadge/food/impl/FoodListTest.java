package wadge.food.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import wadge.food.api.Month;

public class FoodListTest {
    private FoodList foodList;

    @Before
    public void setUp() {
        this.foodList = FoodList.getInstance();
        this.foodList.readFile("food_list.json");
    }

    @Test
    public void testGetFoodFromMonth() {
        Month months[] = Month.values();
        Map<Month, List<Food>> results = new HashMap<>();

        List.of(months).stream().forEach(month -> {
            try {
                results.put(month, this.foodList.getFoodFromGivenMonth(month));
            } catch (Exception e) {
                fail("An invalid month was given");
            }
        });

        results.forEach((key, value) -> {
            assertTrue(value.stream().allMatch(food -> 
                food.getAvailability().contains(key.valueOf())));
        });

    }
}