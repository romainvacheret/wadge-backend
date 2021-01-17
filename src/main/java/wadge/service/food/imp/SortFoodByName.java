package wadge.service.food.imp;

import wadge.model.food.Food;
import wadge.model.food.Month;
import wadge.service.food.api.IsortFood;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortFoodByName implements IsortFood {
	public static SortFoodByName instance;
	public static SortFoodByName getInstance(){
		if(instance==null)
			instance=new SortFoodByName();
		return instance;
	}
	private SortFoodByName(){}
	@Override
	public List<Food> sort(List<Food> foods, Month month) {
		return foods.stream().filter(food ->
				food.getAvailability().contains(month)).sorted(Comparator.comparing(Food::getName))
				.collect(Collectors.toList());
	}
}
