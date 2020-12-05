package wadge.backend;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.fridge.impl.Fridge;
import wadge.fridge.impl.FridgeFood;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FridgeController {
    private static final String FRIDGE = "fridge.json";

    @RequestMapping(path = "/fridge", method = RequestMethod.GET)
    public List<FridgeFood> getFridge() {
        Fridge f = Fridge.getInstance();
        f.readFridge(FRIDGE);
        return f.getFood();
    }

    @RequestMapping(path = "/fridge/addition", method = RequestMethod.POST)
    public List<FridgeFood> addToFridge(@RequestBody String foodList) {
        Fridge f = Fridge.getInstance();
        f.readFridge(FRIDGE);
        List<FridgeFood> list = f.stringToFridgeFood(foodList);
        f.addToFridge(list);
        try {
            f.writeFridge(FRIDGE);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // TODO Add return value
        return null;
    }
}
