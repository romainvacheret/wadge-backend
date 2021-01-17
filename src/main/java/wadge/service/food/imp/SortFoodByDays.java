package wadge.service.food.imp;

import wadge.model.food.Food;
import wadge.model.food.Month;
import wadge.service.food.api.IsortFood;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortFoodByDays implements IsortFood {
	
	public static SortFoodByDays instance;
	public static SortFoodByDays getInstance(){
		if(instance==null)
			 instance=new SortFoodByDays();
		return instance;
	}
	private SortFoodByDays(){ }
	@Override
	public List<Food> sort(List<Food> foods, Month month) {
		return foods.stream().filter(food ->
				food.getAvailability().contains(month)).sorted(Comparator.comparing(Food::getDays))
				.collect(Collectors.toList());
	}
}
