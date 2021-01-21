package wadge.dao.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;

public interface IFridgeDao {
    List<FridgeFood> getAllFridge();
    boolean updateFridgeFood(String food, FoodElement element);
    boolean addAllToFridge(List<FridgeFood> food);
    boolean insertFridgeFood(UUID id, FridgeFood food);
    FridgeFood getFridgeFood(String name);
    Map<String, FridgeFood> getFridge();



    // ----------------------------------------------

    boolean insertFridgeFood(FridgeFood food);
    Optional <FridgeFood> getFridgeFoodFromName(String name);
    boolean addFoodElementToFridgeFood(String fridgeFood, FoodElement element);
    void saveData();
    // boolean update
}
