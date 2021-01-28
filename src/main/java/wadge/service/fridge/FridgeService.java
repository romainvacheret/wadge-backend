package wadge.service.fridge;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IFridgeDao;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;
import wadge.model.fridge.FridgeFoodBuilder;
import wadge.model.fridge.UpdateResponse;


@Service
public class FridgeService {
    private final IFridgeDao fridgeDao;

    public enum RecallType {
        TWO_DAYS, FIVE_DAYS, SEVEN_DAYS, FOURTEEN_DAYS, EXPIRED, OTHER;
    }

    @Autowired
    public FridgeService(@Qualifier("jsonFridgeDao") IFridgeDao fridgeDao) {
        this.fridgeDao = fridgeDao;
    }

    public boolean addAllToFridge(List<FridgeFood> food) {
        // TODO -> dirty code (FridgeFood::addAllProdcuts + fridgeDao::saveDate) -> use two fridgeDao methods ?  
        food.stream().forEach(f -> 
            fridgeDao.getFridgeFoodFromName(f.getName()).ifPresentOrElse(ff -> 
            {ff.addAllProducts(f.getProducts()); fridgeDao.saveData();}, () -> fridgeDao.addAllToFridge(List.of(f)))
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
}
