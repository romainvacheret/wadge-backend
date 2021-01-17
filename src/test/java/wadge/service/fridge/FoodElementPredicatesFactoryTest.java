package wadge.service.fridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import wadge.service.fridge.FridgeService.RecallType;


public class FoodElementPredicatesFactoryTest {
    private FoodElementPredicatesFactory predicatesFactory;

    @Before
    public void setUp() {
        this.predicatesFactory = FoodElementPredicatesFactory.getInstance();
    } 

    @Test
    public void dateDifferenceTest() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date secondDate = calendar.getTime();
        long diff = FoodElementPredicatesFactory.dateDifference(secondDate, currentDate);

        assertEquals(1L, diff);
    }

    @Test
    public void getPredicatesTest() {
        List.of(RecallType.values()).stream().forEach(type -> assertTrue(predicatesFactory.getPredicate(type) instanceof Predicate<?>));
    }
}
