package wadge.service.fridge;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wadge.dao.FridgeFoodRepository;
import wadge.model.food.Food;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Ingredient.Unit;
import wadge.service.food.FoodHelper;
import wadge.service.food.FoodHelper.Conversion;
import wadge.service.food.FoodService;
import wadge.utils.db.SequenceGenerator;


@Service
@AllArgsConstructor
public class FridgeService {
    private final FoodService foodService;
    private final FridgeFoodRepository repository;
    private final SequenceGenerator sequenceGenerator;

    public enum RecallType {
        TWO_DAYS, FIVE_DAYS, SEVEN_DAYS, FOURTEEN_DAYS, EXPIRED, OTHER;
    }

    public void addToFridge(final FridgeFood food) {
        getFridgeFoodFromName(food.getName())
            .ifPresentOrElse(fridgeFood -> {
                final FoodElement foodElement = food.getProducts().values().stream().toList().get(0);
                final Optional<FoodElement> optnl = fridgeFood.getProducts().values().stream()
                    .filter(product -> product.getInsertionDate().equals(
                        foodElement.getInsertionDate()
                    )).findFirst();

                optnl.ifPresentOrElse(optn -> { // If same date
                    final FoodElement previous = optnl.get();
                    previous.setQuantity(previous.getQuantity() + foodElement.getQuantity());
                }, () -> fridgeFood.addAllProducts(
                    food.getProducts().values().stream().toList())
                );
                repository.save(fridgeFood);
            },
            () -> {
                food.setId(sequenceGenerator.generateSequence("fridgefood_sequence"));
                repository.insert(food);
            });
    }

    public void addAllToFridge(final List<FridgeFood> foodList) {
        foodList.forEach(this::addToFridge);
    }

    public List<FridgeFood> getAllFridge() {
        return repository.findAll();
    }

    @SuppressWarnings("unchecked")
    private List<FridgeFood> getExpirationDateFromPredicate(final Predicate<FoodElement> predicate) {
        return (List<FridgeFood>) repository.findAll().stream().map(food -> {
            final Map<Long, FoodElement> futureElements = food.getProducts().values().stream()
                    .filter(predicate)
                    .collect(Collectors.toMap(FoodElement::getId, element -> element));

            return futureElements.isEmpty() ? Optional.empty() :
                Optional.ofNullable(FridgeFood.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .products(futureElements)
                        .build());
        }).flatMap(Optional::stream).toList();
    }

    public List<FridgeFood> getExpirationList(final RecallType type) {
        final FoodElementPredicatesFactory factory = FoodElementPredicatesFactory.getInstance();
        return getExpirationDateFromPredicate(factory.getPredicate(type));
    }

    public void updateFoodElement(final long fridgeFoodId, final FoodElement element) {
        repository.findById(fridgeFoodId).ifPresent(optnl -> {
            if (element.getQuantity() == 0) {
                optnl.getProducts().remove(element.getId());
                // TODO -> improve logic, refactor?
                if(optnl.getProducts().isEmpty()) {
                    repository.delete(optnl);
                    return;
                }
            } else {
                optnl.getProducts().put(element.getId(), element);

            }
            repository.save(optnl);
        });
    }

    public Optional<FridgeFood> getFridgeFoodFromName(final String foodName) {
        return getAllFridge().stream()
            .filter(food -> food.getName().equals(foodName))
            .findFirst();
    }

    public Optional<FridgeFood> getFridgeFoodFromId(final long id) {
        return repository.findById(id);
    }

    // TODO refactor -> change String to Enum
    public String isInFridge(final Ingredient ingredient) {
        final Unit unit = Ingredient.getUnit(ingredient.getName());
        double quantity = 0;
        final List<FridgeFood> fridge = getAllFridge();
        final Optional<Food> optional = foodService.getFoodFromString(Ingredient.extractName(ingredient));

        if(optional.isPresent()){
            final Food food = optional.get();
            final Optional<FridgeFood> fridgeFood = fridge.stream().
                filter(f -> f.getName().equals(food.getName())).findFirst();

            if(fridgeFood.isPresent() && !fridgeFood.get().getProducts().isEmpty()){
                final Ingredient i = new Ingredient(food.getName(), ingredient.getQuantity());
                try {
                    if(!unit.equals(Unit.NONE)) {
                        quantity = Double.parseDouble(i.getQuantity());
                        quantity = unit.equals(Unit.G) ? quantity : quantity * 1000;
                        quantity = FoodHelper.convert(Conversion.G_TO_UNIT, food, quantity);
                    }else{
                        quantity = Double.parseDouble(ingredient.getQuantity());
                    }
                } catch(NumberFormatException e) { // empty string, let quantity to 0
                    e.printStackTrace();
                }

                final int fridgeSum = fridgeFood.get().getProducts().values().stream()
                        .map(FoodElement::getQuantity).reduce(0, Integer::sum);
                return fridgeSum >= quantity ? "present" : "partiellement";
            }
            return "absent";
        }
        else {
            return "default";
        }
    }

    public void emptyFridge() {
        repository.deleteAll();
    }
}
