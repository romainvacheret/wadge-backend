package wadge.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.fridge.DeleteResponse;
import wadge.model.fridge.FridgeFood;
import wadge.model.fridge.UpdateResponse;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FridgeController {
    private final FridgeService fridgeService;
    private final ObjectMapper mapper;

    @Autowired
    public FridgeController(FridgeService fridgeService) {
        mapper = new ObjectMapper();
        this.fridgeService = fridgeService;
    }

    @GetMapping(path="/fridge")
    public List<FridgeFood> getAllFridge() {
        
        return fridgeService.getAllFridge();
    }

    @PostMapping(path = "/fridge/addition")
    public boolean addAllToFridge(@RequestBody JsonNode food) {
        List<FridgeFood> list = Arrays.asList(this.mapper.convertValue(food, FridgeFood[].class));
        fridgeService.addAllToFridge(list);
        return true;
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
        List<UpdateResponse> updateList = Arrays.asList(this.mapper.convertValue(food, UpdateResponse[].class));
        return fridgeService.updateFridge(updateList);
    }

    @PostMapping(path = "/fridge/delete")
    public List<FridgeFood> deleteUsingId(@RequestBody JsonNode ids) {
        List<DeleteResponse> deleteList = Arrays.asList(this.mapper.convertValue(ids, DeleteResponse[].class));
        
        return fridgeService.deleteUsingId(deleteList.stream().map(x -> 
        Map.entry(x.getId(), x.getFridgeFood())).collect(Collectors.toSet()));
    }
}
