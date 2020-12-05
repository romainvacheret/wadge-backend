package wadge.food.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.food.api.IFoodList;
import wadge.food.api.Month;

public class FoodList implements IFoodList {
    private List<Food> foods;
    private final ObjectMapper mapper;
    private static FoodList instance;


    public FoodList() {
        // this.foods = new HashSet<>();
        this.foods = new ArrayList<>();
        this.mapper = new ObjectMapper();
    }

    public static FoodList getInstance() {
        if (instance == null) {
            instance = new FoodList();
        }
        return instance;
    }

    @Override
    public void getFoodFromGivenMonth(Month month) {
        // TODO Auto-generated method stub
        this.foods.stream().filter(food -> food.getAvailability().contains(month.valueOf())).forEach(System.out::println);
    }

    @Override
    public void readFile(String fileName) {
        try {
            foods = Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), Food[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {
        FoodList f = new FoodList();
        f.readFile("food_list2.json");
        f.getFoodFromGivenMonth(Month.DECEMBER);
    }
}
