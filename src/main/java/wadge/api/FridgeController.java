package wadge.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path="/fridge", method=RequestMethod.GET)
    public List<FridgeFood> getAllFridge() {
        return fridgeService.getAllFridge();
    }

    @RequestMapping(path = "/fridge/addition", method= RequestMethod.POST)
    public boolean addAllToFridge(@RequestBody JsonNode food) {
        System.out.println(food);
        List<FridgeFood> list = Arrays.asList(this.mapper.convertValue(food, FridgeFood[].class));
        fridgeService.addAllToFridge(list);
        System.out.println(fridgeService.getAllFridge());
        return true;
    }

    @RequestMapping(path = "/alerts", method= RequestMethod.GET)
    public  Map<String, List<FridgeFood>> getExpirationAlerts() {
        Map<String, List<FridgeFood>> result = new HashMap<>();
        Arrays.asList(RecallType.values()).forEach(type -> 
            result.put(type.toString(), fridgeService.getExpirationList(type))
        );
        System.out.println(result);
        return result;
    }

    @RequestMapping(path = "fridge/update", method= RequestMethod.POST)
    public List<FridgeFood> deleteFromFridge(@RequestBody JsonNode food) {
        List<UpdateResponse> updateList = Arrays.asList(this.mapper.convertValue(food, UpdateResponse[].class));
        // return fridgeService.deleteFromFridge(list);
        return null;
    }
}
