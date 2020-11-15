package wadge.food_list;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

public class FoodList {
    private FoodList() {}

    public static List<Map<String, Object>> readFile(String fileName) {
        JSONParser jsonP = new JSONParser();
        List<Map<String, Object>> list = new ArrayList<>();
        
        try{
            Object obj = jsonP.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get("aliments");
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject aliment = iterator.next();
                list.add(Map.of(
                    "nom",aliment.get("nom"),
                    "type",aliment.get("type"),
                    "vie",aliment.get("vie"),
                    "consommation",(((JSONArray) aliment.get("consommation")).toArray())
                ));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Map<String, Object>> foodFromMonth(String month) throws Exception {
        List<String> months = List.of("janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre");

        if(!months.contains(month)) {
            throw new Exception("Invalid month.");
        }

        List<Map<String, Object>> generalList = readFile("food_list.json");
        List<Map<String, Object>> foodList = new ArrayList<>();

        generalList.forEach(food -> {
            final List<String> listCompare;
            Object[] tmp = (Object[]) food.get("consommation");
            listCompare = Arrays.asList(tmp).stream().map(obj -> Objects.toString(obj, null)).collect(Collectors.toList());
                if(listCompare.contains(month)) {
                    foodList.add(Map.of(
                        "nom", food.get("nom"),
                        "type", food.get("type")
                    ));
                }
        });

        return foodList;
    }
}
