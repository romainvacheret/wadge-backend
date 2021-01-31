package wadge.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path="/foods", method=RequestMethod.GET)
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @RequestMapping(path="/foods/{month}", method=RequestMethod.GET)
    public List<Food> getFoodFromMonth(@PathVariable String month) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
       return foodService.getFoodFromGivenMonth(Month.valueOf(month));
    }

    @RequestMapping(path="/foods/{month}/days", method=RequestMethod.GET)
    public List<Food> getFoodFromMonthByDays(@PathVariable String month) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
       return foodService.sortByDays(foodService.getFoodFromGivenMonth(Month.valueOf(month)));
    }    
}
