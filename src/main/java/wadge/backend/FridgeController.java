package wadge.backend;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.fridge.impl.Fridge;
import wadge.fridge.impl.FridgeFood;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FridgeController {
    private static final String FRIDGE = "fridge2.json";

    @RequestMapping(path="my-fridge", method=RequestMethod.GET)
    public List<FridgeFood> getFridge() {
        Fridge f = Fridge.getInstance();
        f.readFridge(FRIDGE);
        return f.getFood();
    }
}
