package wadge.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.food.Month;
import wadge.model.food.Food;
import wadge.service.food.FoodService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping(path="/foods")
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping(path="/foods/{month}")
    public List<Food> getFoodFromMonth(@PathVariable String month) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
       return foodService.getFoodFromGivenMonth(Month.valueOf(month));
    }

    @GetMapping(path="/foods/{month}/days")
    public List<Food> getFoodFromMonthByDays(@PathVariable String month) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
       return foodService.sortByDays(foodService.getFoodFromGivenMonth(Month.valueOf(month)));
    }    
}
