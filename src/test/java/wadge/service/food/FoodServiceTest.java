package wadge.service.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.food.ConversionRequest;
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

    @Test
    public void sortByDays() {
        List<Food> food = new ArrayList<>();
        List<Food> foodT = new ArrayList<>();
        List<Month> months = List.of(Month.MARCH, Month.APRIL);
        Food carotte = Food.builder()
                        .name("carotte")
                        .type("legume")
                        .availability(months)
                        .days(15)
                        .weight(60)
                        .bulk(false)
                        .build();

        Food courgette = Food.builder()
                        .name("courgette")
                        .type("legume")
                        .availability(months)
                        .days(15)
                        .weight(60)
                        .bulk(false)
                        .build();

        food.add(carotte);
        food.add(courgette);

        foodT.add(courgette);
        foodT.add(carotte);
        assertEquals(foodT,service.sortByDays(food));
    }

    @Test
    public void getFoodFromString() {
        String str = "amandes";
        List<Month> months = List.of(Month.MARCH, Month.APRIL);
        Food carotte = Food.builder()
                        .name("carotte")
                        .type("legume")
                        .availability(months)
                        .days(15)
                        .weight(60)
                        .bulk(false)
                        .build();
        Optional<Food> f = Optional.of(carotte);
        assertEquals(f,service.getFoodFromString(str));
    }

    @Test
    public void convert() {
        ConversionRequest cr = new ConversionRequest();
        cr.setFood("abricot");
        cr.setQuantity(55);
        cr.setType(FoodHelper.Conversion.G_TO_UNIT);
        Optional<Double> d = Optional.of(1.0);
         assertEquals(d, service.convert(cr));
    }
}
