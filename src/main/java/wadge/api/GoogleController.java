package wadge.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.google.Search;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GoogleController {  
    @RequestMapping(path = "/map", method= RequestMethod.GET)
    public ResponseEntity<JSONObject> getCloseShops() {
        Search s = new Search();
        JSONObject json = s.request();
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }
}