package wadge.service.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import wadge.model.food.Food;
import wadge.model.food.Month;
import wadge.service.food.FoodHelper.Conversion;

public class FoodHelperTest {
    private Food f1;
    private Food f2;
    private Food f3;

    @Before
    public void setUp() {
        f1 = new Food("abricot", "legume", new Month[] {Month.JUNE, Month.JULY, Month.AUGUST}, 7, 55, false);
        f2 = new Food("amande", "fruit", new Month[] {Month.SEPTEMBER, Month.OCTOBER}, 150, 100, true);
        f3 = new Food("noisette", "fruit", new Month[] {Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER}, 60, 100, true);
    }
    
    @Test
    public void fromUnitToGTest() {
        assertEquals(110, FoodHelper.fromUnitToG(f1, 2));
        assertEquals(1000, FoodHelper.fromUnitToG(f2, 10));
        assertEquals(300, FoodHelper.fromUnitToG(f3, 3));
    }

    @Test
    public void fromGToUnitTest() {
        assertEquals(7, FoodHelper.fromGToUnit(f1, 345));
        assertEquals(2, FoodHelper.fromGToUnit(f2, 134));
        assertEquals(34, FoodHelper.fromGToUnit(f3, 3400));
    }

    @Test
    public void convertTest() {
        assertEquals(7, FoodHelper.convert(Conversion.G_TO_UNIT ,f1, 345));
        assertEquals(1000, FoodHelper.convert(Conversion.UNIT_TO_G, f2, 10));
    }
}
