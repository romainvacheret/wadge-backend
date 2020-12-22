package wadge.backend;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.food.api.Month;
import wadge.food.impl.Food;
import wadge.food.impl.FoodList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FoodController {
    private static final String FOOD_LIST = "food_list.json";
    private static final FoodList foodList;

    static {
        foodList = FoodList.getInstance();
        foodList.readFile(FOOD_LIST);
    }

    @RequestMapping(path="/foods", method=RequestMethod.GET)
    public List<Food> getFridgeList() {
        return foodList.getFoods();
    }

    @RequestMapping(path = "/foods/{month}", method= RequestMethod.GET)
    public List<Food> getMonth(@PathVariable("month") String month) throws Exception {
        if (month.length() != 0) {
            month = month.toUpperCase();
        }

        return foodList.getFoodFromGivenMonth(Month.valueOf(month));
    }
}
