package wadge.fridge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DisplayFridge {
    private DisplayFridge() {}

    public static List<Map<String, Object>> displayFridge(String fileName) {
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
