package wadge.fridge.impl;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class FoodElementPredicatesFactoryTest {
    private FoodElementPredicatesFactory predicatesFactory;

    @Before
    public void setUp() {
        this.predicatesFactory = FoodElementPredicatesFactory.getInstance();
    } 

    @Test
    public void dateProcessingTest() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date secondDate = calendar.getTime();
        long diff = FoodElementPredicatesFactory.dateDifference(secondDate, currentDate);

        assertEquals(diff, 1L);
    }
}
