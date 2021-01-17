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

    // @GetMapping
    @RequestMapping(path="/foods", method=RequestMethod.GET)
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping("/foods/{month}/{choice}")
    public List<Food> getFoodFromMonth(@PathVariable String month,@PathVariable int choice) {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
        if(choice==0)
               return foodService.sortedByName(Month.valueOf(month));
       return  foodService.sortedByPeremption(Month.valueOf(month));
    }
    @GetMapping("/Allfoods/{peremptionMonth}")
    public List<Food> getFoodsSortByName(@PathVariable String month ){
        if (month.length() != 0) {
            month = month.toUpperCase();
        }
        return  foodService.sortedByName(Month.valueOf(month));
    }

    
}
