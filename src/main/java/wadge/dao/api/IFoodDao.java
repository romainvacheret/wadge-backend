package wadge.dao.api;

import java.util.List;

import wadge.model.food.Food;

public interface IFoodDao {
    List<Food> getAllFoods();
}
