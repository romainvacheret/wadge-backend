package wadge.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wadge.food_list.FoodList;

import wadge.fridge.Fridge;
import wadge.fridge.ExpirationRecall;
import wadge.fridge.ExpirationRecall.RecallType;

import wadge.google.Search;

import wadge.recipe.Recipe;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HelloController {
    private static List<String> expirationTypes = List.of("TWO_DAYS", "FIVE_DAYS", "SEVEN_DAYS", "FORTEEN_DAYS", "EXPIRED", "OTHER");
    private static String FRIDGE_FILE = "fridge.json";
    private static String FOOD_FILE = "food_list.json";

    @RequestMapping(path="/food", method= RequestMethod.POST)
    public void createFridge(@RequestBody List<Map<String,  Object>> foodlist) {
         Fridge.writeFridge(foodlist);
      
    }

    @RequestMapping(path="/fridge", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> readMyFridge() {
        // List<Map<String, Object>> list = Fridge.readFridge(FRIDGE_FILE);
        // return new ResponseEntity<>(list, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(path="/fridge/food", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> readFridge2() {
        List<Map<String, Object>> list = Fridge.readFile(FRIDGE_FILE);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/food_list", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> readFile() {
        List<Map<String, Object>> list = FoodList.readFile(FOOD_FILE);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/filter/{month}", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getMonth(@PathVariable("month") String month){
        if (month.length() != 0) {
            month = month.toLowerCase();
        }

        List<Map<String, Object>> list = null;
        try {
            list = FoodList.foodFromMonth(month);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/map/{lat}/{lng}", method= RequestMethod.GET)
    public ResponseEntity<JSONObject> getCloseShops(@PathVariable("lat") double lat, @PathVariable("lng") double lng) {
        Search s = new Search();
        JSONObject json = s.request(lat, lng);
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }

    @Nullable
    @RequestMapping(path = "/alert/{type}", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getExpirationAlert(@PathVariable("type") String type) {
        if(!expirationTypes.contains(type)) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ExpirationRecall recall = new ExpirationRecall();
        List<Map<String, String>> result = recall.getExpirationList(RecallType.valueOf(type));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/alerts", method= RequestMethod.GET)
    public ResponseEntity<Map<String, List<Map<String, String>>>> getExpirationAlerts() {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        ExpirationRecall recall = new ExpirationRecall();

        expirationTypes.forEach(type -> {
            System.out.println(type);
            result.put(type, recall.getExpirationList(RecallType.valueOf(type)));
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path="/recipes", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getRecipes() {
        List<Map<String, Object>> result = Recipe.readRecipes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/display-fridge", method= RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> readFridge() {
        List<Map<String, Object>> list = Fridge.readFile(FRIDGE_FILE);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}