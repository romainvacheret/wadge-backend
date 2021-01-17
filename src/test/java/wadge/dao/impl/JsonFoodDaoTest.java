package wadge.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.model.food.Food;

public class JsonFoodDaoTest {
    private JsonFoodDao dao;

    @Before
    public void setUp() {
        dao = new JsonFoodDao();
    }

    @Test
    public void getAllFoodsTest() {
        
        assertTrue(dao.getAllFoods() instanceof List<?>);
        assertTrue(dao.getAllFoods().get(0) instanceof Food);
    }
}
