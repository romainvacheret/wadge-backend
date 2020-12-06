package wadge.backend;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.food.impl.Food;
import wadge.food.impl.FoodList;

@CrossOrigin(origins = "http;//localhost:3000")
@RestController
public class FoodListController {
    private static final String FOOD_LIST = "food_list2.json";

    @RequestMapping(path="/foods", method=RequestMethod.GET)
    public List<Food> getFridgeList() {
        FoodList foodList = FoodList.getInstance();
        foodList.readFile(FOOD_LIST);
        return foodList.getFoods();
    }
}
