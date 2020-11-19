package wadge.fridge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static wadge.fridge.Fridge.writeFridge;

public class DeleteFood {

    public static void delete(List<Map<String, Object>> deleteList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> fridgeList = mapper.readValue(Files.readAllBytes(Paths.get("fridge1.json")), new TypeReference<List<Map<String, Object>>>(){});
        fridgeList.forEach(foodElement -> {
            deleteList.forEach(foodDelete -> {
                if(foodElement.get("nom").equals(foodDelete.get("nom"))) {
                    Object productList = foodElement.get("produits");
                    Object productDelete = foodDelete.get("produits");
                    ((List<Map<String, Object>>) productList).forEach(productL -> {
                        ((List<Map<String, Object>>) productDelete).forEach(productD -> {
                            if(productL.get("dateAjout").equals(productD.get("dateAjout"))){
                                Integer qDelete = (Integer) productD.get("quantite");
                                Integer qStrock = (Integer) productL.get("quantite");
                                int quantiteFinal = qStrock - qDelete;
                                if(quantiteFinal == 0) {
                                    productL.clear();
                                }
                                else productL.replace("quantite" ,quantiteFinal);
                            }
                        });
                    });
                }
            });
        });
        writeFridge(fridgeList);
    }
}
