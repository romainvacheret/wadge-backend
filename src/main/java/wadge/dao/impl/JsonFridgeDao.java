package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IFridgeDao;
import wadge.model.fridge.FridgeFood;

@Repository("jsonFridgeDao")
public class JsonFridgeDao implements IFridgeDao {
    private final List<FridgeFood> fridge;
    private final ObjectMapper mapper;
    static final String FILE_NAME = "fridge.json";


    public JsonFridgeDao() {
        mapper = new ObjectMapper();
        fridge = new ArrayList<>();

        try {
            fridge.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), FridgeFood[].class)));
        } catch (IOException e) {
            // logger.log(Level.FINE, e.getMessage());
        }
    }

    @Override
    public boolean addAllToFridge(List<FridgeFood> food) {
        boolean rtr = fridge.addAll(food);
        if(rtr) {
            try {
            mapper.writeValue(Paths.get(FILE_NAME).toFile(), fridge);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                rtr = false;
            }
        }
        return rtr;
    }

    @Override
    public List<FridgeFood> getAllFridge() {
        return fridge;
    }
    
}
