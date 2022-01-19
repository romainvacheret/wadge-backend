package wadge.service.shopping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wadge.model.food.Food;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Ingredient.Unit;
import wadge.service.food.FoodService;
import wadge.service.food.FoodHelper;
import wadge.service.food.FoodHelper.Conversion;

@Service
@AllArgsConstructor
public class ShoppingService {
    // TODO -> add DB
    private final Map<String, Ingredient> shoppingList = new HashMap<>();
    private final FoodService foodService;

    public Set<Ingredient> getShoppingList() {
        return shoppingList.values().stream().collect(Collectors.toSet());
    }

    public Set<Ingredient> deleteFromShoppingList(final Set<String> elements) {
        elements.stream().forEach(name -> shoppingList.remove(name));
        return getShoppingList();
    }

    // TODO -> refactor
    public Set<Ingredient> addToShoppingList(final Set<Ingredient> elements) {
        elements.stream().forEach(element -> {
            
            final Optional<Food> food = foodService.getFoodFromString(Ingredient.extractName(element));
            final String previousName = element.getName();
            if(food.isPresent()) {
                element.setName(food.get().getName());
            }

            // The name has been changed if is in food_list
            final Unit unit = Ingredient.getUnit(previousName);
            final Ingredient previousValue = shoppingList.get(element.getName());
            double quantity = 0;
            // The element's unit is Kg, g or neither one
            try {
                if(!unit.equals(Unit.NONE)) {
                    quantity = Double.parseDouble(element.getQuantity());
                    quantity = unit.equals(Unit.G) ? quantity : quantity * 1000;
                    quantity = FoodHelper.convert(Conversion.G_TO_UNIT, food.get(), quantity);
                } else {
                    quantity = Double.parseDouble(element.getQuantity());
                }
            } catch(NumberFormatException e) { // Unparsable strings ex: "un brin", "une feuille"...
                e.printStackTrace();
            }

            if(quantity == -1) {
                quantity = 0;
            }

            final Double value = previousValue == null ? quantity : Double.parseDouble(previousValue.getQuantity());
            final String quantityAsString = String.format("%.1f", value).replace(",", ".") ;

            shoppingList.put(element.getName(), element);
        });

        return getShoppingList();
    }

}
