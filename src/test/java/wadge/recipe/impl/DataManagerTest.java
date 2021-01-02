package wadge.recipe.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import wadge.recipe.api.IDataManager;

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
        assertTrue(dataManager.readFile("recipe_list.json") instanceof List<?>);
        assertTrue(dataManager.readFile("recipe_list.json").get(0) instanceof Recipe);
    }
}
