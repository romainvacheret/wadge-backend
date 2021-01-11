package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IFoodDao;
import wadge.model.food.Food;

@Repository("jsonFoodDao")
public class JsonFoodDao implements IFoodDao {
    private final List<Food> food;
    private final ObjectMapper mapper;
    private static final String FILE_NAME = "food_list.json";

    public JsonFoodDao() {
        food = new ArrayList<>();
        mapper = new ObjectMapper();
        try {
            food.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), Food[].class)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<Food> getAllFoods() {
        return food;
    }
    
}
