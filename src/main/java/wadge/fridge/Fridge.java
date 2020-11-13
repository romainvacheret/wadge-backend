package wadge.fridge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Fridge {
    public static void writeFridge(List<Map<String, Object>> foodlist) {
        JSONArray fridge = new JSONArray();
            foodlist.forEach(foodElement -> {
                JSONObject food = new JSONObject();
                food.put("nom", foodElement.get("nom"));
                Object products = foodElement.get("produits");
                JSONArray list = new JSONArray();
                ((List<Map<String, String>>) products).forEach(product -> {
                    JSONObject obj = new JSONObject();
                    obj.put("dateLimite", product.get("dateLimite"));
                    obj.put("dateAjout", product.get("dateAjout"));
                    obj.put("quantite", product.get("quantite"));
                    list.add(obj);
                });
                food.put("produits",list);
                fridge.add(food);
            });

        try (FileWriter file = new FileWriter("fridge1.json")) {
            file.write(fridge.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(fridge);
    }
}
