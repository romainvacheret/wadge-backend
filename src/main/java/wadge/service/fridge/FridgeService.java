package wadge.service.fridge;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IFridgeDao;
import wadge.dao.impl.JsonFridgeDao;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;

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
        return fridgeDao.addAllToFridge(food);
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
                result.add(new FridgeFood(food.getName(), elements));
            }
        });
        return result;
    }

    public List<FridgeFood> getExpirationList(RecallType type) {
        FoodElementPredicatesFactory factory = FoodElementPredicatesFactory.getInstance();
        return getExpirationDateFromPredicate(factory.getPredicate(type));
    }

    public List<FridgeFood> deleteFromFridge(List<FridgeFood> deleteList) {
        List<FridgeFood> fridgeList = fridgeDao.getAllFridge();
        fridgeList.forEach(foodFridge -> {
            deleteList.forEach(foodDelete -> {
                String food = foodFridge.getName();
                String del = foodDelete.getName();
                if(food.equals(del)){
                    List<FoodElement> fridge = foodFridge.getProducts();
                    List<FoodElement> delete = foodDelete.getProducts();
                    fridge.forEach(foodElement -> {
                        delete.forEach(deleteElement -> {
                            if(foodElement.getInsertionDate().equals(deleteElement.getInsertionDate())){
                                int result;
                                result = foodElement.getQuantity() - deleteElement.getQuantity();
                                foodElement.setQuantity(result);
                                if(result <= 0){
                                    fridge.remove(foodElement);
                                }
                            }
                        });
                    });
                    if(foodFridge.getProducts().isEmpty()){
                        fridgeList.remove(foodFridge);
                    }
                }
            });
        });
        //fridgeDao.setFridge(fridgeList);
        return fridgeList;
    }
}
