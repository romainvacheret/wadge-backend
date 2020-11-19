package wadge.fridge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

public class Fridge {
    public static void writeFridges(List<Map<String,Object>> foodlist) {
        
        JSONArray fridge = new JSONArray();
            foodlist.forEach(foodElement -> {
                JSONObject food = new JSONObject();
                food.put("nom", foodElement.get("nom"));
               // Object products = foodElement.get("products");
               Map products =( Map)foodElement.get("products");
               
                JSONObject obj = new JSONObject();
                obj.put("datelimite",products.get("datelimite"));
                obj.put("dateAjoutee", products.get("dateAjoutee"));
                obj.put("quantity",products.get("quantity"));

                food.put("produits",obj);
                fridge.add(food);
            });

        try (FileWriter file = new FileWriter("fridge.json")) {
            file.write(fridge.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> toto = mapper.readValue(Files.readAllBytes(Paths.get("fridge1.json")), new TypeReference<List<Map<String, Object>>>(){});
        addFood(toto);
    }

    public static void addFood(List<Map<String,Object>> foodlist) {
        List<Map<String, Object>> fridgeList = readFile("fridge2.json");
        fridgeList.forEach(foodElement ->
                foodlist.forEach(foodAdd -> {
                    if (foodElement.get("nom").equals(foodAdd.get("nom"))) {
                        Object productList = foodElement.get("produits");
                        Object productAdd = foodAdd.get("produits");
                        //((List<Map<String, Object>>) productAdd).forEach(productD -> ((List<Map<String, Object>>) productList).add(productD));
                    }
                }));
         // writeFridge(foodlist);
    }

    public static List<Map<String, Object>> readFile(String fileName) {
        JSONParser jsonP = new JSONParser();
        List<Map<String, Object>> list = new ArrayList<>();

        try{
            Object obj = jsonP.parse(new FileReader(fileName));
            //JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) obj;
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject aliment = iterator.next();
                list.add(Map.of(
                        "nom",aliment.get("nom"),
                        "produits",(((JSONArray) aliment.get("produits")).toArray())
                ));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}