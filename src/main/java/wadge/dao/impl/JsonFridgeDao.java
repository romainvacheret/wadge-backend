package wadge.dao.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IFridgeDao;
import wadge.model.fridge.FridgeFood;

@Repository("jsonFridgeDao")
public class JsonFridgeDao implements IFridgeDao {
    private final Map<UUID, FridgeFood> fridge;
    private final ObjectMapper mapper;
    static final String FILE_NAME = "fridge.json";
    private static Logger logger = Logger.getLogger(JsonFridgeDao.class.getName());

    public JsonFridgeDao() {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        fridge = new HashMap<>();

        try {
            List<FridgeFood> list = Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), FridgeFood[].class));
            addAllToFridge(list);
        } catch (IOException e) {
            logger.log(Level.FINE, e.getMessage());
        }
    }

    @Override
    public boolean addAllToFridge(List<FridgeFood> food) {
        boolean rtr = food.stream().map(aFood -> {
            Optional<UUID> id = aFood.getId();
            return id.isPresent() ? insertFridgeFood(id.get(), aFood) : insertFridgeFood(aFood);
        }).allMatch(a -> a);

        System.out.println(fridge);
        if (rtr) {
            try {
                mapper.writeValue(Paths.get(FILE_NAME).toFile(), fridge.values());
            } catch (IOException e) {
                logger.log(Level.FINE, e.getMessage());
                rtr = false;
            }
        }

        return rtr;
    }

    @Override
    public List<FridgeFood> getAllFridge() {
        return fridge.values().stream().collect(Collectors.toList());
    }

    @Override
    public boolean insertFridgeFood(UUID id, FridgeFood food) {
        boolean rtr = true;
        try {
            System.out.println(food);
            this.fridge.put(id, food);
        } catch(Exception e) {
            logger.log(Level.FINE, e.getMessage(), e);
            rtr = false;
        }

        return rtr;
    }

}
