package wadge.service.food;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IFoodDao;
import wadge.model.food.Food;
import wadge.model.food.Month;
import wadge.service.food.api.IsortFood;
import wadge.service.food.imp.SortFoodByDays;
import wadge.service.food.imp.SortFoodByName;

@Service
public class FoodService {
    private final IFoodDao foodDao;

    @Autowired
    public FoodService(@Qualifier("jsonFoodDao") IFoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public List<Food> getAllFood() {
        return foodDao.getAllFoods();
    }

    public List<Food> getFoodFromGivenMonth(Month month) {
        List<Food> foods = foodDao.getAllFoods();
        
        return foods.stream().filter(food ->
                food.getAvailability().contains(month)).collect(Collectors.toList());
    }
    public List<Food> sortedByName(Month month){
        List<Food> foods = foodDao.getAllFoods();
        IsortFood sort= SortFoodByName.getInstance();
        return sort.sort(foods,month);
    }
    public List<Food> sortedByPeremption(Month month){
        List<Food> foods = foodDao.getAllFoods();
        IsortFood sort= SortFoodByDays.getInstance();
        return sort.sort(foods,month);
        
    }
    
   
}
