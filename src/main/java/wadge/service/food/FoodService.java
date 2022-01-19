package wadge.service.food;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wadge.dao.FoodRepository;
import wadge.model.food.ConversionRequest;
import wadge.model.food.Food;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Ingredient.Unit;
import wadge.service.food.FoodHelper.Conversion;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository repository;

    public List<Food> getAllFood() {
        return repository.findAll();
    }

    public List<Food> getFoodFromGivenMonth(final Month month) {
        return getAllFood().stream()
            .filter(food -> food.getAvailability()
            .contains(month)).toList();
    }

    public List<Food> sortByDays(final List<Food> food) {
        return food.stream().sorted(Comparator.comparing(Food::getDays)).toList();
    }  

    // TODO refactor -> should no longer be used
    @Deprecated(since="19/01/2022")
    public Optional<Food> getFoodFromString(final String string) {
        return getAllFood().stream().filter(food -> string.contains(food.getName())).findFirst();
    }

    public Optional<Double> convert(final ConversionRequest request) {
        final Optional<Food> food = getFoodFromString(request.getFood());
         return  food.isPresent() ?
            Optional.of(FoodHelper.convert(request.getType(), food.get(), request.getQuantity())) :
            Optional.empty();
    }

    // TODO -> refactor
    public double getUnits(final Ingredient ingredient) {
        double rtr = 0;
        double ingQuantity;
        final Unit unit = Ingredient.getUnit(ingredient.getName());
        final Optional<Food> food = getFoodFromString(Ingredient.extractName(ingredient));

        try {
            ingQuantity = Double.parseDouble(ingredient.getQuantity());
        } catch(NumberFormatException e) {
            ingQuantity = -1;
        }

        if(ingQuantity == -1){
            rtr = 0;
        } else {
            if(unit.equals(Unit.NONE)) {
                rtr = ingQuantity;
            } else {
                final double quantity = unit.equals(Unit.G) ? ingQuantity : ingQuantity * 1000;
                rtr =   food.isPresent() ? FoodHelper.convert(Conversion.G_TO_UNIT, food.get(), quantity) :
                    quantity / 100;
            }
        }

        return rtr;
    }   
}
