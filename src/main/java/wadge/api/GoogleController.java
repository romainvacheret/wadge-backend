package wadge.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import wadge.service.map.MapService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GoogleController {  
    @GetMapping(path = "/map")
    public ResponseEntity<JSONObject> getCloseShops() {
        MapService s = new MapService();
        JSONObject json = s.request();
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }
}