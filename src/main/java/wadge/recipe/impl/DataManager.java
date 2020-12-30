package wadge.recipe.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.recipe.api.IDataManager;

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
    public List<Recipe> readFile(String fileName) {
        List<Recipe> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), Recipe[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
        List<Recipe> list = DataManager.getInstance().readFile("recipe_list2.json");
        System.out.println(list);
    }
}
