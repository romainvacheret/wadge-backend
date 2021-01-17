package wadge.service.recipe.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService.RecallType;

public class FridgeSelectionTest {
    private FridgeSelection fridgeSelection;

    @Before
    public void setUp() {
        Recipe r1 = new Recipe("Test1", List.of("step1", "step2"), 4, 45, 2, "http://www.aLink.com", 
            List.of(new Ingredient("I1", "I2"), new Ingredient("I2", "4")));
        Recipe r2 = new Recipe("Test2", List.of("step1", "step2"), 4, 45, 2, "http://www.aLink.com", 
            List.of(new Ingredient("I11", "6"), new Ingredient("I12", "7")));
        Recipe r3 = new Recipe("Test3", List.of("step1", "step2"), 4, 45, 2, "http://www.aLink.com", 
            List.of(new Ingredient("I21", "3"), new Ingredient("I4", "2")));
        List<Recipe> recipes = List.of(r1, r2, r3);
        Map<RecallType, List<String>> products = Map.of(
            RecallType.OTHER, List.of("I22"),
            RecallType.FIVE_DAYS, List.of("I2"),
            RecallType.TWO_DAYS, List.of("I1", "I12"));
        fridgeSelection = new FridgeSelection(recipes, products);
    }

    @Test
    public void addToSetTest() {
        Set<String> aSet = new HashSet<>();
        fridgeSelection.addToSet(aSet, "key1", 3);
        assertTrue(aSet.contains("key1")); 
        assertEquals(Integer.valueOf(3), fridgeSelection.scoringMap.get("key1")); 
    }

    // @Test
    // public void defineScoringMapTest() {
    //     Map<RecallType, List<String>> products = Map.of(
    //         RecallType.OTHER, List.of("I22"),
    //         RecallType.FIVE_DAYS, List.of("I2"),
    //         RecallType.TWO_DAYS, List.of("I1", "I12"));
        
    //     fridgeSelection.defineScoringMap(products);
    //     assertTrue(fridgeSelection.scoringMap.containsKey("I22"));
    //     assertTrue(fridgeSelection.scoringMap.containsKey("I12"));
    //     assertTrue(fridgeSelection.scoringMap.containsKey("I2"));
    //     assertEquals(3, fridgeSelection.scoringMap.keySet().size());

    //     assertEquals(fridgeSelection.scoringMap.get("I22"));
    //     assertEquals(fridgeSelection.scoringMap.get("I12"));
    //     assertEquals(fridgeSelection.scoringMap.get("I2"));
    //     assertEquals(3, fridgeSelection.scoringMap.keySet().size());
    // }
}
