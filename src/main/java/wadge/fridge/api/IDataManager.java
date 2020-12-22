package wadge.fridge.api;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import wadge.fridge.impl.FridgeFood;

public interface IDataManager {
    List<FridgeFood> readFile(String fileName);
    List<FridgeFood> readJson(JsonNode node);
    void writeFile(List<FridgeFood> data, String fileName) throws IOException;
}
