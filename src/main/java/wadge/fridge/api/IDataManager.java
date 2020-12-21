package wadge.fridge.api;

import java.io.IOException;
import java.util.List;

import wadge.fridge.impl.FridgeFood;

public interface IDataManager {
    List<FridgeFood> readFile(String fileName);
    void writeFile(List<FridgeFood> data, String fileName) throws IOException;
}
