package Wadge.BackEnd;


import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileReader;
import java.util.*;


@RestController
public class HelloController {

    @CrossOrigin
    @RequestMapping("/food_list")
    public ResponseEntity<List<Map<String,String>>> ReadFiles() throws java.io.IOException{
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
                m.put("consommation",((ArrayList) aliment.get("consommation")).toString());
                list.add(m);
            }
        }
       catch (Exception e) {
            e.printStackTrace();
       }
        return new ResponseEntity<List<Map<String,String>>>(list, HttpStatus.OK);
    }
}