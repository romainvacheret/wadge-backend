package wadge.fridge.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.fridge.api.IDataManager;

public class DataManagerTest {
    private IDataManager dataManager;

    @Before
    public void setUp() {
        dataManager = DataManager.getInstance();
    }

    @Test
    public void getInstanceTest() {
        IDataManager m1 = DataManager.getInstance();

        assertTrue(dataManager instanceof DataManager);
        assertSame(dataManager,m1);
    }

    @Test
    public void readFileTest() {
        assertTrue(dataManager.readFile("fridge.json") instanceof List<?>);
        assertTrue(dataManager.readFile("fridge.json").get(0) instanceof FridgeFood);
    }
}
