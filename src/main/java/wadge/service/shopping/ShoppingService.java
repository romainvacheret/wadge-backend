package wadge.service.shopping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import wadge.model.food.Food;
import wadge.model.shopping.ShoppingElement;
import wadge.service.food.FoodService;

@Service
public class ShoppingService {
    private Map<String, Double> shoppingList = new HashMap<>();
    private FoodService foodService;

    public ShoppingService(FoodService foodService) {
        this.foodService = foodService;
    }

    public Map<String, Double> getShoppingList() {
        return shoppingList;
    }
    
    public Map<String, Double> addToShoppingList(Set<ShoppingElement> elements) {
        elements.stream().forEach(element -> {
            // The element is contained in food_list or not
            Optional<Food> food = foodService.getFoodFromString(element.getName());
            String name = food.isPresent() ? food.get().getName() : element.getName();

            if(shoppingList.containsKey(name)) {
                shoppingList.put(name, shoppingList.get(name) + element.getQuantity());
            } else {
                shoppingList.put(name, element.getQuantity());
            }
        });
        return shoppingList;
    }

}
