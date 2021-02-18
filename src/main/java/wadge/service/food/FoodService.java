package wadge.service.food;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IFoodDao;
import wadge.model.food.ConversionRequest;
import wadge.model.food.Food;
import wadge.model.food.Month;

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

    public List<Food> sortByDays(List<Food> food) {
        return food.stream().sorted(Comparator.comparing(Food::getDays)).collect(Collectors.toList());
    }  

    public Optional<Food> getFoodFromString(String string) {
        return getAllFood().stream().filter(food -> string.contains(food.getName())).findFirst();
    }

    public Optional<Double> convert(ConversionRequest request) {
        Optional<Food> f = getFoodFromString(request.getFood());
         return  f.isPresent() ?
            Optional.of(FoodHelper.convert(request.getType(), f.get(), request.getQuantity())) :
            Optional.empty();
    }
   
}
