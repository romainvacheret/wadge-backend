package wadge.backend;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.fridge.api.IDataManager;
import wadge.fridge.impl.DataManager;
import wadge.fridge.impl.ExpirationRecall;
import wadge.fridge.impl.ExpirationRecall.RecallType;
import wadge.fridge.impl.Fridge;
import wadge.fridge.impl.FridgeFood;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FridgeController {
    private static final String FRIDGE_FILE = "fridge.json";
    private static final Fridge fridge;

    static {
        fridge = Fridge.getInstance();
        fridge.readFridge(FRIDGE_FILE);
    }

    @RequestMapping(path = "/fridge", method = RequestMethod.GET)
    public List<FridgeFood> getFridge() {
        return fridge.getFood();
    }

    @RequestMapping(path = "/fridge/addition", method = RequestMethod.POST)
    public List<FridgeFood> addToFridge(@RequestBody JsonNode foodList) {
        IDataManager manager = DataManager.getInstance();
        List<FridgeFood> list = manager.readJson(foodList);
        fridge.addToFridge(list);
        try {
            fridge.writeFridge(FRIDGE_FILE);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // TODO Add return value
        return null;
    }

    @RequestMapping(path = "/alerts", method= RequestMethod.GET)
    public  Map<String, List<FridgeFood>> getExpirationAlerts() {
        Map<String, List<FridgeFood>> result = new HashMap<>();
        Arrays.asList(RecallType.values()).forEach(type -> 
            result.put(type.toString(), ExpirationRecall.getExpirationList(type, FRIDGE_FILE))
        );

        return result;
    }
}
