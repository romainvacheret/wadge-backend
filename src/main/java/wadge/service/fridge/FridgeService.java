package wadge.service.fridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wadge.dao.FridgeFoodRepository;
import wadge.dao.api.IFridgeDao;
import wadge.model.food.Food;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FridgeFood;
import wadge.model.fridge.FridgeFoodBuilder;
import wadge.model.fridge.UpdateResponse;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Ingredient.Unit;
import wadge.service.food.FoodHelper;
import wadge.service.food.FoodHelper.Conversion;
import wadge.service.food.FoodService;
import wadge.utils.db.SequenceGenerator;

import javax.swing.text.html.Option;


@Service
@AllArgsConstructor
public class FridgeService {
    private final IFridgeDao fridgeDao;
    private final FoodService foodService;
    private final FridgeFoodRepository repository;
    private final SequenceGenerator sequenceGenerator;

    public enum RecallType {
        TWO_DAYS, FIVE_DAYS, SEVEN_DAYS, FOURTEEN_DAYS, EXPIRED, OTHER;
    }

    // @Autowired
    // public FridgeService(@Qualifier("jsonFridgeDao") IFridgeDao fridgeDao,FoodService foodService) {
    //     this.fridgeDao = fridgeDao;
    //     this.foodService = foodService;
    // }



    // TODO -> refactor
    public void addToFridge(final FridgeFood food) {
        System.out.println(food.getId());
        /*
        repository.findById(food.getId()).ifPresentOrElse(
            optnl -> optnl.addAllProducts(food.getProducts().values().stream().toList()), 
            () -> repository.insert(food));

         */
        System.out.println("cii");
        System.out.println(repository.findAll());
        if(food.getId() == null) {
            food.setId(sequenceGenerator.generateSequence("fridgefood_sequence"));
            repository.insert(food);
        } else {
            repository.findById(food.getId()).ifPresentOrElse(
                optnl -> optnl.addAllProducts(food.getProducts().values().stream().toList()),
                () -> repository.insert(food));
        }
    }

    public boolean addAllToFridge(List<FridgeFood> foodList) {
        // // TODO -> dirty code (FridgeFood::addAllProdcuts + fridgeDao::saveDate) -> use two fridgeDao methods ?  
        // foodList.stream().forEach(f ->
        //     fridgeDao.getFridgeFoodFromName(f.getName()).ifPresentOrElse(ff -> {ff.addAllProducts(f.getProducts()); fridgeDao.saveData();}, () -> fridgeDao.addAllToFridge(List.of(f)))
        // );
        // return true;
        // foodList.stream().forEach(food -> {
        //     final foodToUpdate = repository.findById(food.getId());
            
        // });
        // fridgeService.findAll().stream().forEach(f ->
        //     fridgeDao.getFridgeFoodFromName(f.getName()).ifPresentOrElse(ff -> {ff.addAllProducts(f.getProducts()); fridgeDao.saveData();}, () -> fridgeDao.addAllToFridge(List.of(f)))
        // );
        // return true;
        System.out.println(foodList);
        foodList.stream().forEach(this::addToFridge);
        return true;
    }

    public List<FridgeFood> getAllFridge() {
        // return fridgeDao.getAllFridge();
        return repository.findAll();
    }

    // TODO -> refactor
    private List<FridgeFood> getExpirationDateFromPredicate(Predicate<FoodElement> predicate) {
        List<FridgeFood> fridgeList = repository.findAll(); // fridgeDao.getAllFridge();
        List<FridgeFood> result = new ArrayList<>();
        fridgeList.forEach(food -> {
            // List<FoodElement> elements = food.getProducts().values().stream().filter(predicate).toList();
            Map<Long, FoodElement> futureElements = food.getProducts().values().stream()
                .filter(predicate)
                .collect(Collectors.toMap(element -> element.getId(), element -> element));
            if(!futureElements.isEmpty()) {
                // result.add(new FridgeFoodBuilder().withName(food.getName()).withProducts(elements).createFridgeFood());
                result.add(FridgeFood.builder()
                    .id(food.getId())
                    .name(food.getName())
                    .products(futureElements)
                    .build());
            }
        });
        return result;
    }

    // TODO -> refactor
    public List<FridgeFood> getExpirationList(RecallType type) {
        FoodElementPredicatesFactory factory = FoodElementPredicatesFactory.getInstance();
        return getExpirationDateFromPredicate(factory.getPredicate(type));
    }

    public Optional<FridgeFood> updateFridgeFood(FridgeFood food) {
        if(food.getProducts().values().size() == 0) {
            repository.delete(food);
            return Optional.empty();
        } else {
            return Optional.ofNullable(repository.save(food));
        }
    }

    public void updateFoodElement(long fridgeFoodId, FoodElement element) {
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

    // TODO -> change logic and delete: use Ids instead
    public Optional<FridgeFood> getFridgeFoodFromName(final String foodName) {
        return getAllFridge().stream()
                .filter(food -> food.getName().equals(foodName))
                .findFirst();
    }
    public Optional<FridgeFood> getFridgeFoodFromId(final long id) {
        return repository.findById(id);
    }

/*
    public List<FridgeFood> updateFridge(List<UpdateResponse> updateList) {
        updateList.stream().forEach(update -> {
            int quantity = update.getQuantity();
            String fridgeFood = update.getFridgeFood();
            long id = update.getId();

            if(quantity <= 0) {
                fridgeDao.deleteFromFridge(fridgeFood, id); // TODO -> delete FridgeFood if empty
            } else {
                fridgeDao.getFridgeFoodFromName(fridgeFood).ifPresent(
                    food -> {
                        FoodElement tmp = food.getProducts().get(id);
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
*/

    // TODO -> useless, can use update instead
    public List<FridgeFood> deleteUsingId(Set<Map.Entry<Long, String>> ids) {
        // repository.deleteAllById(ids);
        // ids.stream().forEach(entry -> )
        // System.out.println(ids);
        ids.forEach(entry -> getFridgeFoodFromName(entry.getValue())
            .ifPresent(optnl -> {
                optnl.getProducts().remove(entry.getKey());
                repository.save(optnl);
            }));
        return getAllFridge();
        /*
        fridgeDao.deleteUsingId(ids);
        return fridgeDao.getAllFridge();

         */
    }
    public String isInFridge(Ingredient ingredient) {
        Unit unit = Ingredient.getUnit(ingredient.getName());
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
                } catch(NumberFormatException e) {
                    // empty string, let quantity to 0
                }

                final int fridgeSum = fridgeFood.get().getProducts().values().stream()
                        .map(FoodElement::getQuantity).reduce(0, Integer::sum);
                return fridgeSum >= quantity ? "present" : "partiellement";
            }
            return "absent";
        }
        else{
            return "default";
        }
    }

    public void emptyFridge() {
        repository.deleteAll();
    }

/*
    // TODO -> refactor
    public String isInFridge(Ingredient ingredient) {
        Unit unit = Ingredient.getUnit(ingredient.getName());
        double quantity = 0;
        List<FridgeFood> fridge = fridgeDao.getAllFridge();
        Optional<Food> option = foodService.getFoodFromString(Ingredient.extractName(ingredient));

        if( option.isPresent() ){
            Optional<FridgeFood> fridgeFood = fridge.stream().filter(f -> f.getName().equals(option.get().getName())).findFirst();
            if(fridgeFood.isPresent() && !fridgeFood.get().getProducts().isEmpty()){
                Ingredient i = new Ingredient(option.get().getName(), ingredient.getQuantity());
                try {
                    if(!unit.equals(Unit.NONE)) {
                        quantity = Double.parseDouble(i.getQuantity());
                        quantity = unit.equals(Unit.G) ? quantity : quantity * 1000;
                        quantity = FoodHelper.convert(Conversion.G_TO_UNIT, option.get(), quantity);
                    }else{
                        quantity = Double.parseDouble(ingredient.getQuantity());
                    }
                } catch(NumberFormatException e) {
                    // empty string, let quantity to 0
                }
                
                int fridgeSum = fridgeFood.get().getProducts().values().stream().map(FoodElement::getQuantity).reduce(0,Integer::sum);
                return fridgeSum >= quantity ? "present" : "partiellement";
            }
            return "absent";
        }
        else{
            return "default";
        }
    }
    */
}
