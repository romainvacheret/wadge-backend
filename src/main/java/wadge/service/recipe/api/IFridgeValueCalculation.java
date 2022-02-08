package wadge.service.recipe.api;

import java.util.Map;

import wadge.model.fridge.FridgeFood;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService.RecallType;

@FunctionalInterface
public interface IFridgeValueCalculation {
    double compute(final Recipe recipe, final Map<RecallType, FridgeFood> fridge);
}
