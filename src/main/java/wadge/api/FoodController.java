package wadge.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    // @GetMapping
    @RequestMapping(path="/foods", method=RequestMethod.GET)
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @RequestMapping(path = "/foods/{month}", method= RequestMethod.GET)
    public List<Food> getFoodFromMonth(@PathVariable("month") String month) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }

        return foodService.getFoodFromGivenMonth(Month.valueOf(month));
    }

    
}
