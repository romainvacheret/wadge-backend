package wadge.fridge.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ExpirationRecall {

    public enum RecallType {
        TWO_DAYS, FIVE_DAYS, SEVEN_DAYS, FORTEEN_DAYS, EXPIRED, OTHER;
    }

    private static List<FridgeFood> getExpirationDateFromPredicate(Predicate<FoodElement> predicate, String fileName) {
        Fridge f = Fridge.getInstance();
        List<FridgeFood> fridgeList = f.readFile(fileName);
        List<FridgeFood> result = new ArrayList<>();
        fridgeList.forEach(food -> {
            List<FoodElement> elements = food.getProducts().stream().filter(predicate).collect(Collectors.toList());
            if(!elements.isEmpty()) {
                result.add(new FridgeFood(food.getName(), elements));
            }
        });
        return result;
    }

    public static List<FridgeFood> getExpirationList(RecallType type, String fileName) {
        FoodElementPredicatesFactory factory = FoodElementPredicatesFactory.getInstance();
        return getExpirationDateFromPredicate(factory.getPredicate(type), fileName);
    }   
}
