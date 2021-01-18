package wadge.dao.api;

import java.util.List;

import wadge.model.fridge.FridgeFood;

public interface IFridgeDao {
    List<FridgeFood> getAllFridge();
    boolean addAllToFridge(List<FridgeFood> food);
    void setFridge(List<FridgeFood> fridge);
}
