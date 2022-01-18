package wadge.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.util.BsonUtils;
import org.springframework.web.bind.annotation.*;
import wadge.model.fridge.*;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.utils.db.SequenceGenerator;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class FridgeController {
    private final FridgeService fridgeService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final SequenceGenerator sequenceGenerator;

    @GetMapping(path="/fridge")
    public List<FridgeFood> getAllFridge() {
        
        // return fridgeService.getAllFridge();
        // TODO -> never used? fridge always retrieved using "/alerts"?
        return fridgeService.getAllFridge();
    }

    @PostMapping(path = "/fridge")
    public boolean addAllToFridge(@RequestBody JsonNode food) {
        System.out.println(food);
        List<LoadedFridgeFood> list = Arrays.asList(this.mapper.convertValue(food, LoadedFridgeFood[].class));
        System.out.println(list);
        fridgeService.addAllToFridge(list.stream()
            .map(foodElement -> foodElement.
                toFridgeFood(sequenceGenerator.generateSequence("foodelement_sequence")))
            .toList());
        return true; // TODO -> change logic or delete
    }

    @GetMapping(path= "/fridge/empty")
    public void emptyFridge() {
        fridgeService.emptyFridge();
    }

    @GetMapping(path = "/alerts")
    public  Map<String, List<FridgeFood>> getExpirationAlerts() {
        Map<String, List<FridgeFood>> result = new HashMap<>();
        Arrays.asList(RecallType.values()).forEach(type -> 
            result.put(type.toString(), fridgeService.getExpirationList(type))
        );
        return result;
    }

    @PutMapping(path = "/fridge")
    public List<FridgeFood> deleteFromFridge(@RequestBody JsonNode food) {
        // final List<UpdateResponse> updateList = Arrays.asList(this.mapper.convertValue(food, UpdateResponse[].class));
        final List<FoodElementUpdateResponse> updateList = Arrays.asList(this.mapper.convertValue(food, FoodElementUpdateResponse[].class));

        updateList.stream().forEach(response -> {
            final Optional<FridgeFood> optional = fridgeService.getFridgeFoodFromId(response.getFridgeFood());
            // Optional<FridgeFood> optional = fridgeService.getFridgeFoodFromName(response.getFridgeFood());

            optional.ifPresent(optnl -> {
                // TODO -> Id must be present of raises exception: change logic
                System.out.println(optnl);
                System.out.println(response.getId());
                // TODO -> refactor
                FoodElement foodElement = optnl.getProducts().get(response.getId());
                foodElement.setQuantity(response.getQuantity());
                fridgeService.updateFoodElement(optnl.getId(), foodElement);
            });
        });
        return fridgeService.getAllFridge();
        // return fridgeService.updateFridge(updateList);
    }

    // TODO -> delete, no longer needed ?
    @PostMapping(path = "/fridge/delete")
    public List<FridgeFood> deleteUsingId(@RequestBody JsonNode ids) {
        System.out.println(ids);
        List<DeleteResponse> deleteList = Arrays.asList(this.mapper.convertValue(ids, DeleteResponse[].class));
        
        return fridgeService.deleteUsingId(deleteList.stream().map(x ->
        Map.entry(x.getId(), x.getFridgeFood())).collect(Collectors.toSet()));
    }
}
