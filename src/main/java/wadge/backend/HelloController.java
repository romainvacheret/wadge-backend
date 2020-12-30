package wadge.backend;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.google.Search;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HelloController {  
    @RequestMapping(path = "/map/{lat}/{lng}", method= RequestMethod.GET)
    public ResponseEntity<JSONObject> getCloseShops(@PathVariable("lat") double lat, @PathVariable("lng") double lng) {
        Search s = new Search();
        JSONObject json = s.request(lat, lng);
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }
}