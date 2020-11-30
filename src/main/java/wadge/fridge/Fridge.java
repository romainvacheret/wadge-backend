package wadge.fridge;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Fridge {

    public static void writeFridge(List<Map<String, Object>> newElements) {
        
        List<Map<String, Object>> productsToAdd = formatConverter(newElements);
        System.out.println("Products to add: " + productsToAdd);

        List<Map<String, Object>> currentFridge = Fridge.readFile("fridge.json");
        System.out.println("Current fridge: " + currentFridge);
        mergeList(currentFridge, productsToAdd);

        System.out.println("Fridge after merge: " + currentFridge);


        JSONArray array = new JSONArray();
        array.addAll(currentFridge);

        try (FileWriter file = new FileWriter("fridge.json")) {
            file.write(array.toJSONString());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

// --------------------------------------
    private static List<Map<String, Object>> formatConverter(List<Map<String, Object>> elements) {
        return elements.stream().map(element -> {
            element = (Map<String, Object>) element;
            System.out.println(element);
            Map<String, Object> products = (Map<String, Object>) element.get("produits");
            return Map.of(
                "nom", (String) element.get("nom"),
                "produits", List.of(Map.of(
                    "dateLimite", (String) products.get("dateLimite"),
                    "dateAjout", (String) products.get("dateAjout"),
                    "quantite", products.get("quantite").toString()
                ))
            );
        }).collect(Collectors.toList());
    }

    private static List<String> getKeys(List<Map<String, Object>> list) {
        return list.stream().map(element -> (String) ((Map<String, Object>) element).get("nom")).collect(Collectors.toList());
    }

    private static List<String> listDifference(List<String> l1, List<String> l2) {
        return l1.stream().filter(element -> !l2.contains(element)).collect(Collectors.toList());
    }

    private static Map<String, Object> findElementInList(List<Map<String, Object>> list, String name) {
        List<Map<String, Object>> tmp = list.stream().filter(element -> {
            String elementName = (String) element.get("nom");
            return elementName.equals(name);
        }).collect(Collectors.toList());

        return tmp.isEmpty() ? null : tmp.get(0);
    }

    private static void addProductToElement(Map<String, Object> element, Map<String, Object> elementToAdd) {
        List<Map<String, Object>> products = (List<Map<String, Object>>) element.get("produits");
        products.add(elementToAdd);
    }

    public static void mergeList(List<Map<String, Object>> l1, List<Map<String, Object>> l2) {
        // TODO -> improve: merge of similar products
        List<String> keys1 = getKeys(l1);
        List<String> keys2 = getKeys(l2);

        List<String> keyDifference = listDifference(keys1, keys2);

        l2.forEach(element -> {
            element = (Map<String, Object>) element;
            String elementName = (String) element.get("nom");

            if(keyDifference.contains(elementName)) {
                Map<String, Object> communeElement =  findElementInList(l1, elementName);
                    addProductToElement(communeElement, element);
            } else {
                l1.add(element);
            }
        });
    }

    public static List<Map<String, Object>> readFile(String fileName) {
        JSONParser jsonP = new JSONParser();
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            Object obj = jsonP.parse(new FileReader(fileName));
            JSONArray arr = (JSONArray) obj;
            Iterator<JSONObject> iterator = arr.iterator();
            while (iterator.hasNext()) {
                JSONObject aliment = iterator.next();
                Object[] products = ((JSONArray) aliment.get("produits")).toArray();
                List<Object> convertedProducts = Arrays.asList(products);
                List<Map<String, Object>> convertedPs = convertedProducts.stream().map(product -> (Map<String, Object>) product).collect(Collectors.toList());
                list.add(Map.of(
                    "nom", aliment.get("nom"), 
                    "produits", convertedPs
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}