package wadge.service.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wadge.model.food.Food;
import wadge.service.food.FoodHelper.Conversion;

class FoodHelperTest {
    private Food f1;
    private Food f2;
    private Food f3;

    @BeforeEach
    void setUp() {
        f1 = Food.builder()
                .name("abricot")
                .type("legume")
                .availability(List.of(Month.JUNE, Month.JULY, Month.AUGUST))
                .days(7)
                .weight(55)
                .bulk(true)
                .build();
        f2 = Food.builder()
                .name("amande")
                .type("legume")
                .availability(List.of(Month.SEPTEMBER, Month.OCTOBER))
                .days(150)
                .weight(100)
                .bulk(true)
                .build();
        f3 = Food.builder()
                .name("noisette")
                .type("fruit")
                .availability(List.of(Month.SEPTEMBER, Month.OCTOBER))
                .days(60)
                .weight(100)
                .bulk(true)
                .build();
    }
    
    @Test
    void fromUnitToGTest() {
        assertEquals(110, FoodHelper.fromUnitToG(f1, 2));
        assertEquals(1000, FoodHelper.fromUnitToG(f2, 10));
        assertEquals(300, FoodHelper.fromUnitToG(f3, 3));
    }

    @Test
    void fromGToUnitTest() {
        assertEquals(7, FoodHelper.fromGToUnit(f1, 345));
        assertEquals(2, FoodHelper.fromGToUnit(f2, 134));
        assertEquals(34, FoodHelper.fromGToUnit(f3, 3400));
    }

    @Test
    void convertTest() {
        assertEquals(7, FoodHelper.convert(Conversion.G_TO_UNIT ,f1, 345));
        assertEquals(1000, FoodHelper.convert(Conversion.UNIT_TO_G, f2, 10));
    }
}
