package wadge.recipe.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.logger.WadgeLogger;
import wadge.recipe.api.IDataManager;

public class DataManager implements IDataManager {
    private final ObjectMapper mapper;
    private static IDataManager instance;
    private static Logger logger = WadgeLogger.getLogger();

    private DataManager() {
        this.mapper = new ObjectMapper();
    }

    public static IDataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    @Override
    public List<Recipe> readFile(String fileName) {
        List<Recipe> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), Recipe[].class)));
        } catch (IOException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }
        return list;
    }
}
