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
    private final SequenceGenerator sequenceGenerator;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(path="/fridge")
    public List<FridgeFood> getAllFridge() {
        
        // return fridgeService.getAllFridge();
        // TODO -> never used? fridge always retrieved using "/alerts"?
        return fridgeService.getAllFridge();
    }

    @PostMapping(path = "/fridge")
    public void addAllToFridge(@RequestBody JsonNode food) {
        final List<LoadedFridgeFood> list = Arrays.asList(this.mapper.convertValue(food, LoadedFridgeFood[].class));
        fridgeService.addAllToFridge(list.stream()
            .map(foodElement -> foodElement.
                toFridgeFood(sequenceGenerator.generateSequence("foodelement_sequence")))
            .toList());
    }

    @GetMapping(path= "/fridge/empty")
    public void emptyFridge() {
        fridgeService.emptyFridge();
    }

    @GetMapping(path = "/alerts")
    public Map<String, List<FridgeFood>> getExpirationAlerts() {
        final Map<String, List<FridgeFood>> result = new HashMap<>();
        Arrays.asList(RecallType.values()).forEach(type -> 
            result.put(type.toString(), fridgeService.getExpirationList(type))
        );
        return result;
    }

    @PutMapping(path = "/fridge")
    public List<FridgeFood> deleteFromFridge(@RequestBody JsonNode food) {
        final List<FoodElementUpdateResponse> updateList = Arrays.asList(
                this.mapper.convertValue(food, FoodElementUpdateResponse[].class));

        updateList.stream().forEach(response -> {
            final Optional<FridgeFood> optional = fridgeService.getFridgeFoodFromId(response.getFridgeFood());

            optional.ifPresent(optnl -> {
                final FoodElement foodElement = optnl.getProducts().get(response.getId());
                foodElement.setQuantity(response.getQuantity());
                fridgeService.updateFoodElement(optnl.getId(), foodElement);
            });
        });
        return fridgeService.getAllFridge();
    }
}
