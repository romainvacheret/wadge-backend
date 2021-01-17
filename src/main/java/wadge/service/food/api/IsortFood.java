package wadge.service.food.api;

import wadge.model.food.Food;
import wadge.model.food.Month;

import java.util.List;

public interface IsortFood {
	List<Food> sort(List<Food> foods, Month month);
}
