package wadge.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wadge.food_list.FoodList;
import wadge.fridge.Fridge;
import wadge.fridge.ExpirationRecall;
import wadge.fridge.ExpirationRecall.RecallType;
import wadge.google.Search;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HelloController {

    private static List<String> expirationTypes = List.of("TWO_DAYS", "FIVE_DAYS", "SEVEN_DAYS", "FORTEEN_DAYS", "EXPIRED");

    @RequestMapping("/food_list")
    public ResponseEntity<List<Map<String, Object>>> readFile() {
        List<Map<String, Object>> list = FoodList.readFile("food_list.json");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping("/fridge/food")
    public ResponseEntity<List<Map<String, Object>>> readFridge() {
        List<Map<String, Object>> list = Fridge.readFile("fridge.json");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/filter/{month}")
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

    @RequestMapping("/map/{lat}/{lng}")
    public ResponseEntity<JSONObject> getCloseShops(@PathVariable("lat") double lat, @PathVariable("lng") double lng) {
        Search s = new Search();
        JSONObject json = s.request(lat, lng);
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }
    @Nullable
    @RequestMapping("/alert/{type}")
    public ResponseEntity<List<Map<String, String>>> getExpirationAlert(@PathVariable("type") String type) {
        if(!expirationTypes.contains(type)) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ExpirationRecall recall = new ExpirationRecall();
        List<Map<String, String>> result = recall.getExpirationList(RecallType.valueOf(type));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping("/alerts")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getExpirationAlerts() {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        ExpirationRecall recall = new ExpirationRecall();

        expirationTypes.forEach(type -> {
            result.put(type, recall.getExpirationList(RecallType.valueOf(type)));
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
