package wadge.api;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.fridge.*;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.utils.db.SequenceGenerator;

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

    @PostMapping(path = "/fridge/addition")
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

    @GetMapping(path= "empty")
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

    @PostMapping(path = "/fridge/update")
    public List<FridgeFood> deleteFromFridge(@RequestBody JsonNode food) {
        final List<UpdateResponse> updateList = Arrays.asList(this.mapper.convertValue(food, UpdateResponse[].class));

        updateList.stream().forEach(response -> {
            Optional<FridgeFood> optional = fridgeService.getFridgeFoodFromName(response.getFridgeFood());

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

    @PostMapping(path = "/fridge/delete")
    public List<FridgeFood> deleteUsingId(@RequestBody JsonNode ids) {
        List<DeleteResponse> deleteList = Arrays.asList(this.mapper.convertValue(ids, DeleteResponse[].class));
        
        return fridgeService.deleteUsingId(deleteList.stream().map(x ->
        Map.entry(x.getId(), x.getFridgeFood())).collect(Collectors.toSet()));
    }
}
