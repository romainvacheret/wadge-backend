package wadge.fridge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

public class Fridge {
    public static void writeFridge(List<Map<String,Object>> foodlist) {
        JSONArray fridge = new JSONArray();
            foodlist.forEach(foodElement -> {
                JSONObject food = new JSONObject();
                food.put("nom", foodElement.get("nom"));
               // Object products = foodElement.get("products");
               Map products =( Map)foodElement.get("products");
               //JSONObject job = (JSONObject) products;
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
                        "consommation",(((JSONArray) aliment.get("produits")).toArray())
                ));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
