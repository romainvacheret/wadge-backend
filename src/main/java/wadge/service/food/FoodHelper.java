package wadge.service.food;

import wadge.model.food.Food;

public class FoodHelper {
    private FoodHelper() {}

    public enum Conversion {
        UNIT_TO_G, G_TO_UNIT
    }

    private static double fromUnitToG(Food food, double quantity) {
        return quantity * food.getWeight();
    }

    private static double fromGToUnit(Food food, double quantity) {
        return Math.ceil(quantity / food.getWeight());
    }

    public static double convert(Conversion type, Food food, double quantity) {
        return type == Conversion.UNIT_TO_G ? fromUnitToG(food, quantity) :
            fromGToUnit(food, quantity);
    }
}
