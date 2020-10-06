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
import java.util.stream.Stream;

@RestController
public class HelloController {
    @CrossOrigin
    @RequestMapping("/food_list")
    public ResponseEntity<Map<String, List<String>>> readFile() throws java.io.IOException{
        Path pathIngredients = Paths.get("food_list.txt");
        List<String> ingredients = new ArrayList<>();
        try(Stream<String> input = Files.lines(pathIngredients)) {
            input.forEach(l -> ingredients.add(l));
        }
        
        Map<String, List<String>> m = new HashMap<>();
        m.put("Food", ingredients);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }
}