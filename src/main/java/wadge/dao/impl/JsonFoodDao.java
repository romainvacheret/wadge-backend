package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IFoodDao;
import wadge.model.food.Food;

@Repository("jsonFoodDao")
public class JsonFoodDao implements IFoodDao {
    private final List<Food> food;
    private final ObjectMapper mapper;
    static final String FILE_NAME = "food_list.json";
    private static Logger logger = Logger.getLogger(JsonFoodDao.class.getName());

    public JsonFoodDao() {
        food = new ArrayList<>();
        mapper = new ObjectMapper();
        try {
            food.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), Food[].class)));
        } catch (IOException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }
    }

    @Override
    public List<Food> getAllFoods() {
        return food;
    }
    
}
