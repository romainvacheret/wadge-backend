package wadge.service.fridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IFridgeDao;
import wadge.model.food.Food;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;
import wadge.model.fridge.FridgeFoodBuilder;
import wadge.model.fridge.UpdateResponse;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Ingredient.Unit;
import wadge.service.food.FoodHelper;
import wadge.service.food.FoodService;
import wadge.service.food.FoodHelper.Conversion;


@Service
public class FridgeService {
    private final IFridgeDao fridgeDao;
    private FoodService foodService;

    public enum RecallType {
        TWO_DAYS, FIVE_DAYS, SEVEN_DAYS, FOURTEEN_DAYS, EXPIRED, OTHER;
    }

    @Autowired
    public FridgeService(@Qualifier("jsonFridgeDao") IFridgeDao fridgeDao,FoodService foodService) {
        this.fridgeDao = fridgeDao;
        this.foodService = foodService;
    }

    public boolean addAllToFridge(List<FridgeFood> food) {
        // TODO -> dirty code (FridgeFood::addAllProdcuts + fridgeDao::saveDate) -> use two fridgeDao methods ?  
        food.stream().forEach(f ->
            fridgeDao.getFridgeFoodFromName(f.getName()).ifPresentOrElse(ff -> {ff.addAllProducts(f.getProducts()); fridgeDao.saveData();}, () -> fridgeDao.addAllToFridge(List.of(f)))
        );
        return true;
    }

    public List<FridgeFood> getAllFridge() {
        return fridgeDao.getAllFridge();
    }

    private List<FridgeFood> getExpirationDateFromPredicate(Predicate<FoodElement> predicate) {
        List<FridgeFood> fridgeList = fridgeDao.getAllFridge();
        List<FridgeFood> result = new ArrayList<>();
        fridgeList.forEach(food -> {
            List<FoodElement> elements = food.getProducts().stream().filter(predicate).collect(Collectors.toList());
            if(!elements.isEmpty()) {
                result.add(new FridgeFoodBuilder().withName(food.getName()).withProducts(elements).createFridgeFood());
            }
        });
        return result;
    }

    public List<FridgeFood> getExpirationList(RecallType type) {
        FoodElementPredicatesFactory factory = FoodElementPredicatesFactory.getInstance();
        return getExpirationDateFromPredicate(factory.getPredicate(type));
    }

    public List<FridgeFood> updateFridge(List<UpdateResponse> updateList) {
        updateList.stream().forEach(update -> {
            int quantity = update.getQuantity();
            String fridgeFood = update.getFridgeFood();
            UUID id = update.getId();

            if(quantity <= 0) {
                fridgeDao.deleteFromFridge(fridgeFood, id); // TODO -> delete FridgeFood if empty
            } else {
                fridgeDao.getFridgeFoodFromName(fridgeFood).ifPresent(
                    food -> {
                        FoodElement tmp = food.getProducts2().get(id);
                        if(quantity < tmp.getQuantity()) {
                            tmp.setQuantity(quantity); // TODO -> use a fridgeDao method 
                        }
                    }
                );
            }
        });
        fridgeDao.saveData(); 
        return fridgeDao.getAllFridge();
    }

    public List<FridgeFood> deleteUsingId(Set<Map.Entry<UUID, String>> ids) {
        fridgeDao.deleteUsingId(ids);
        return fridgeDao.getAllFridge();
    }

    public String isInFridge(Ingredient ingredient) {
        Unit unit = Ingredient.getUnit(ingredient.getName());
        double quantity = 0;
        List<FridgeFood> fridge = fridgeDao.getAllFridge();
        Optional<Food> option = foodService.getFoodFromString(Ingredient.extractName(ingredient));

        if( option.isPresent() ){
            Optional<FridgeFood> fridgeFood = fridge.stream().filter(f -> f.getName().equals(option.get().getName())).findFirst();
            if(fridgeFood.isPresent() && !fridgeFood.get().getProducts().isEmpty()){
                Ingredient i = new Ingredient(option.get().getName(), ingredient.getQuantity());
                if(!unit.equals(Unit.NONE)) {
                    quantity = Double.parseDouble(i.getQuantity());
                    quantity = unit.equals(Unit.G) ? quantity : quantity * 1000;
                    quantity = FoodHelper.convert(Conversion.G_TO_UNIT, option.get(), quantity);
                }else{
                    quantity = Double.parseDouble(ingredient.getQuantity());
                }
                int fridgeSum = fridgeFood.get().getProducts().stream().map(FoodElement::getQuantity).reduce(0,Integer::sum);
                return fridgeSum >= quantity ? "present" : "partiellement";
            }
            return "absent";
        }
        else{
            return "default";
        }
    }
}
