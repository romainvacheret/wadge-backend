package wadge.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import wadge.model.fridge.FoodElement;
import wadge.model.fridge.FoodElementUpdateResponse;
import wadge.model.fridge.FridgeFood;
import wadge.model.fridge.LoadedFridgeFood;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.utils.db.SequenceGenerator;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class FridgeController {
    private final FridgeService fridgeService;
    private final SequenceGenerator sequenceGenerator;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(path="/fridge")
    public List<FridgeFood> getAllFridge() {
        // TODO -> never used? fridge always retrieved using "/alerts"?
        return fridgeService.getAllFridge();
    }

    @PostMapping(path = "/fridge")
    public void addAllToFridge(@RequestBody final JsonNode food) {
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

    // TODO refactor -> change String to RecallType
    @GetMapping(path = "/alerts")
    public Map<String, List<FridgeFood>> getExpirationAlerts() {
        return Arrays.stream(RecallType.values()).collect(Collectors.toMap(
            RecallType::toString, fridgeService::getExpirationList));
    }

    @PutMapping(path = "/fridge")
    public List<FridgeFood> updateFromFridge(@RequestBody final JsonNode food) {
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
