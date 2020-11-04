package Wadge.BackEnd;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Wadge.google.Search;


@RestController
public class HelloController {
    @CrossOrigin
    @RequestMapping("/food_list")
    public ResponseEntity<List<Map<String,String>>> readFile() {
        JSONParser jsonP = new JSONParser();
        List<Map<String,String>> list = new ArrayList<>();
        try{
            Object obj = jsonP.parse(new FileReader("food_list.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("aliments");
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                Map m = new HashMap<String, String>();
                JSONObject aliment = iterator.next();
                m.put("nom",aliment.get("nom"));
                m.put("type",aliment.get("type"));
                m.put("consommation",( aliment.get("consommation")).toString());
                list.add(m);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(path = "/filter/{month}")
    public ResponseEntity<List<Map<String,String>>> getMonth(@PathVariable("month") String month){
        if (month.length() != 0) {
            month = month.toLowerCase();
        }
        JSONParser jsonP = new JSONParser();
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Object obj = jsonP.parse(new FileReader("food_list.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("aliments");
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                Map mapFilter = new HashMap<String, String>();
                JSONObject aliment = iterator.next();
                List<String> listCompare;
                listCompare = (ArrayList) aliment.get("consommation");
                if (listCompare.contains(month)) {
                    mapFilter.put("nom", aliment.get("nom"));
                    mapFilter.put("type", aliment.get("type"));
                    list.add(mapFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping("/map/{lat}/{lng}")
    public ResponseEntity<JSONObject> getCloseShops(@PathVariable("lat") double lat, @PathVariable("lng") double lng) {
        Search s = new Search();
        JSONObject json = s.request(lat, lng);
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", s.parseJSON((JSONArray) json.get("candidates")));
        return new ResponseEntity<>(tmp, HttpStatus.OK);
    }
}