package wadge.food.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.food.api.IFoodList;
import wadge.food.api.Month;
import wadge.food.exceptions.ReadFileFirstException;
import wadge.logger.WadgeLogger;

public class FoodList implements IFoodList {
    private List<Food> foods;
    private final ObjectMapper mapper;
    private static FoodList instance;
    private static Logger logger = WadgeLogger.getLogger();


    private FoodList() {
        this.foods = null;
        this.mapper = new ObjectMapper();
    }

    public static FoodList getInstance() {
        if (instance == null) {
            instance = new FoodList();
        }
        return instance;
    }

    @Override
    public List<Food> getFoodFromGivenMonth(Month month) throws Exception {
        if(this.foods == null) {
            throw new ReadFileFirstException("Call FoodList::readFile first.");
        }
        return this.foods.stream().filter(food -> 
            food.getAvailability().contains(month.valueOf())).collect(Collectors.toList());
    }

    @Override
    public void readFile(String fileName) {
        try {
            foods = Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), Food[].class));
        } catch (IOException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }
	}

    public List<Food> getFoods() { return this.foods; }
}
