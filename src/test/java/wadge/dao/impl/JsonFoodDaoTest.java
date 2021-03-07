package wadge.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.food.Food;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonFoodDaoTest {
    @Autowired
    private JsonFoodDao dao;

    @Test
    public void getAllFoodsTest() {
        
        assertTrue(dao.getAllFoods() instanceof List<?>);
        assertTrue(dao.getAllFoods().get(0) instanceof Food);
    }
}
