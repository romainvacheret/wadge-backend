package Wadge.BackEnd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @CrossOrigin
    @RequestMapping("/food_list")
    public ResponseEntity<Map> ReadFiles() throws java.io.IOException{
        Path pathIngredients = Paths.get("food_list.txt");
        List<String> ingredients = new ArrayList<>();
        Files.lines(pathIngredients).forEach(l-> {
            ingredients.add(l);
        });
        Map m = new HashMap<String, String>();
        m.put("Food", ingredients);
        return new ResponseEntity<Map>(m, HttpStatus.OK);
    }
    //public String index() { return "Greetings from Spring Boot!"; }
}