package wadge.dao.api;

import java.util.List;
import java.util.UUID;

import wadge.model.fridge.FridgeFood;

public interface IFridgeDao {
    List<FridgeFood> getAllFridge();
    boolean addAllToFridge(List<FridgeFood> food);
    boolean insertFridgeFood(UUID id, FridgeFood food);

    default boolean insertFridgeFood(FridgeFood food) {
        UUID id = UUID.randomUUID();
        food.setFood(id);
        return insertFridgeFood(id, food);
    }
}
