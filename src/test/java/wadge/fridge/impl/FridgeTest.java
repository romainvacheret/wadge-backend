package wadge.fridge.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FridgeTest {
    private Fridge fridge;

    @Before
    public void setUp() {
        fridge = Fridge.getInstance();
    }

    @Test
    public void getInstanceTest() {
        Fridge f1 = Fridge.getInstance();

        assertTrue(fridge instanceof Fridge);
        assertSame(fridge, f1);
    }

    @Test
    public void readFridgeTest() {
        assertTrue(fridge.getFood().isEmpty());
        fridge.readFridge("fridge.json");
        assertFalse(fridge.getFood().isEmpty());
        
    }

    @Test
    public void getFoodTest() {
        assertTrue(fridge.getFood() instanceof List<?>);
        assertTrue(fridge.getFood().get(0) instanceof FridgeFood);
    }

    @Test
    public void addToFridgeTest() {
        List<FridgeFood> food = List.of(new FridgeFood("Food", new FoodElement[] {new FoodElement("date 1", "date 2", 42), }));
        fridge.getFood().clear();
        fridge.addToFridge(food);
        assertEquals(food, fridge.getFood());
    }
}
