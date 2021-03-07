package wadge.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.model.fridge.FridgeFood;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonFridgeDaoTest {
    @Autowired
    private JsonFridgeDao dao;

    @Test
    public void getAllFridgeTest() {
        
        assertTrue(dao.getAllFridge() instanceof List<?>);
        assertTrue(dao.getAllFridge().get(0) instanceof FridgeFood);
    }

    // @Test
    // public void addAllToFridgeTest(){
    //     fail("TODO");
    //     // TODO
    // }
}