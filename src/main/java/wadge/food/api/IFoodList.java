package wadge.food.api;

import java.util.List;

import wadge.food.impl.Food;

public interface IFoodList {
     void readFile(String fileName);
     List<Food> getFoodFromGivenMonth(Month month) throws Exception;
}
