package wadge.dao.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.model.fridge.FridgeFood;

public class JsonFridgeDaoTest {
    private JsonFridgeDao dao;

    @Before
    public void setUp() {
        dao = new JsonFridgeDao();
    }

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