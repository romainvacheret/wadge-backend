package wadge.service.food;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.dao.api.IFoodDao;
import wadge.dao.impl.JsonFoodDao;
import wadge.model.food.Food;
import wadge.model.food.Month;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodServiceTest {
    @Autowired
    private FoodService service;

    @Test
    public void getAllFoodTest() {
        assertTrue(service.getAllFood() instanceof List<?>);
        assertTrue(service.getAllFood().get(0) instanceof Food);
    }

    @Test
    public void testGetFoodFromMonth() {
        Month months[] = Month.values();
        Map<Month, List<Food>> results = new HashMap<>();

        List.of(months).stream().forEach(month -> 
                results.put(month, service.getFoodFromGivenMonth(month))
        );

        results.forEach((key, value) -> {
            assertTrue(value.stream().allMatch(food -> 
                food.getAvailability().contains(key)));
        });

    }
}
