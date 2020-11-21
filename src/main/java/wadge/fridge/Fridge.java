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
                JSONObject product = (JSONObject) aliment.get("products");
                JSONObject object = new JSONObject();
                object.put("datelimite", product.get("datelimite"));
                object.put("dateAjoutee", product.get("dateAjoutee"));
                object.put("quantity", Integer.parseInt(product.get("quantity").toString()));
                food.put("products", object);
                System.out.println(food);
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
            Map product = (Map) frigoElement.get("products");
            System.out.println("le produit existants:"+product);
            try {
                JSONObject obj = new JSONObject();
                obj.put("dateAjoutee", product.get("dateAjoutee"));
                obj.put("datelimite", product.get("datelimite"));
                obj.put("quantity", product.get("quantity"));
                food.put("products", obj);
                System.out.println("OBJET 1" + food);
                fridge.add(food);
            } catch (Exception e) {
            }
        });
        
        foodlist.forEach(foodElement -> {
            JSONObject food = new JSONObject();
            food.put("nom", foodElement.get("nom"));
            Map product = (Map) foodElement.get("products");

            try {
                JSONObject obj = new JSONObject();
                obj.put("dateAjoutee", product.get("dateAjoutee"));
                obj.put("datelimite", product.get("datelimite"));
                obj.put("quantity", Integer.parseInt((String) product.get("quantity")));
                food.put("products", obj);
                System.out.println("OBJET 2" + food);
                fridge.add(food);

            } catch (Exception e) {
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