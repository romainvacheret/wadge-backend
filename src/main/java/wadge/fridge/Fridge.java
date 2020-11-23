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


public class Fridge {
    public static List<Map<String, Object>> readFridge(String fileName) {
        JSONParser jsonP = new JSONParser();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Object obj = jsonP.parse(new FileReader(fileName));
            JSONArray arr = (JSONArray) obj;
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject food = new JSONObject();
                JSONObject aliment = iterator.next();
                food.put("nom", aliment.get("nom"));
                JSONObject product = (JSONObject) aliment.get("produits");
                JSONObject object = new JSONObject();
                object.put("dateLimite", product.get("dateLimite"));
                object.put("dateAjout", product.get("dateAjout"));
                object.put("quantite", Integer.parseInt(product.get("quantite").toString()));
                food.put("produits", object);
                list.add(food);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeFridge(List<Map<String, Object>> foodlist) {
        JSONArray fridge = new JSONArray();
        List<Map<String, Object>> frigo = Fridge.readFridge("fridge.json");
        frigo.forEach(frigoElement -> {
            JSONObject food = new JSONObject();
            food.put("nom", frigoElement.get("nom"));
            Map product = (Map) frigoElement.get("produits");
            try {
                JSONObject obj = new JSONObject();
                obj.put("dateAjout", product.get("dateAjout"));
                obj.put("dateLimite", product.get("dateLimite"));
                obj.put("quantite", product.get("quantite"));
                food.put("produits", obj);
                fridge.add(food);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        foodlist.forEach(foodElement -> {
            JSONObject food = new JSONObject();
            food.put("nom", foodElement.get("nom"));
            Map product = (Map) foodElement.get("produits");

            try {
                JSONObject obj = new JSONObject();
                obj.put("dateAjout", product.get("dateAjout"));
                obj.put("dateLimite", product.get("dateLimite"));
                obj.put("quantite", Integer.parseInt((String) product.get("quantite")));
                food.put("produits", obj);
                fridge.add(food);

            } catch (Exception e) {
                e.printStackTrace();
            }

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

        try {
            Object obj = jsonP.parse(new FileReader(fileName));
            // JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) obj;
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject aliment = iterator.next();
                list.add(Map.of("nom", aliment.get("nom"), "produits",
                        (((JSONArray) aliment.get("produits")).toArray())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}