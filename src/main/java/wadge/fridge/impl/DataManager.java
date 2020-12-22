package wadge.fridge.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.fridge.api.IDataManager;

public class DataManager implements IDataManager {
    private final ObjectMapper mapper;
    private static IDataManager instance;

    private DataManager() {
        this.mapper = new ObjectMapper();
    }

    public static IDataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    @Override
    public List<FridgeFood> readFile(String fileName) {
        List<FridgeFood> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), FridgeFood[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void writeFile(List<FridgeFood> data, String fileName) throws IOException {
        mapper.writeValue(Paths.get(fileName).toFile(), data);
    }

    @Override
    public List<FridgeFood> readJson(JsonNode node) {
        return Arrays.asList(this.mapper.convertValue(node, FridgeFood[].class));
    }
}
