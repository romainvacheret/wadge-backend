package wadge.service.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.FakeRepository;
import wadge.dao.FoodRepository;
import wadge.model.food.ConversionRequest;
import wadge.model.food.Food;
import java.time.Month;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataMongoTest
class FoodServiceTest {

    @MockBean
    private FoodRepository foodRepository;
    private FoodService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FoodService(foodRepository);
        when(foodRepository.findAll()).thenReturn(FakeRepository.getFood());
    }

    @Test
    void getAllFood() {
        underTest.getAllFood();
        verify(foodRepository).findAll();
    }

    @Test
    void getFoodFromGivenMonth() {
        final Month[] months = Month.values();
        final Map<Month, List<Food>> results = List.of(months).stream().collect(Collectors.toMap(
            month -> month,
            underTest::getFoodFromGivenMonth
        ));

        results.forEach((key, value) ->
            assertTrue(value.stream().allMatch(food -> 
                food.getAvailability().contains(key)))
        );
    }

    @Test
    void sortByDays() {
        final List<Food> foodTest = FakeRepository.getFood();
        final List<Food> result = List.of(foodTest.get(2),
            foodTest.get(0), foodTest.get(1), foodTest.get(3));
        assertEquals(result , underTest.sortByDays(foodTest));
    }

    @Test
    void getFoodFromString() {
        final Optional<Food> food = Optional.ofNullable(FakeRepository.getFood().get(0));
        assertEquals(food,underTest.getFoodFromString("fraise"));
    }

    @Test
    void convert() {
        final ConversionRequest cr = ConversionRequest.builder()
            .food("mangue")
            .quantity(200)
            .type(FoodHelper.Conversion.G_TO_UNIT)
            .build();
        final Optional<Double> d = Optional.of(1.0);
        final ConversionRequest cr2 = ConversionRequest.builder()
            .food("mais")
            .quantity(2)
            .type(FoodHelper.Conversion.UNIT_TO_G)
            .build();
        final Optional<Double> d2 = Optional.of(400.0);
        final ConversionRequest cr3 = ConversionRequest.builder()
            .food("abricot")
            .quantity(55)
            .type(FoodHelper.Conversion.G_TO_UNIT)
            .build();

        assertEquals(d, underTest.convert(cr));
        assertEquals(d2, underTest.convert(cr2));
        assertEquals(Optional.empty(), underTest.convert(cr3));
    }
}
