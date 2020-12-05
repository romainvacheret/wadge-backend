package wadge.fridge.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import wadge.fridge.api.IFridge;

public class Fridge implements IFridge {
    private List<FridgeFood> foods;
    private final ObjectMapper mapper;
    private static Fridge instance;

    private Fridge() {
        this.mapper = new ObjectMapper();
        this.foods = new ArrayList<>();
    }

    public static Fridge getInstance() {
        if (instance == null) {
            instance = new Fridge();
        }
        return instance;
    }

    @Override
    public List<FridgeFood> readFile(String fileName) {
        List<FridgeFood> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(), FridgeFood[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void readFridge(String fileName) {
        this.foods = this.readFile(fileName);
    }

    @Override
    public void writeFridge(String fileName) throws IOException {
        mapper.writeValue(Paths.get(fileName).toFile(), this.foods);
    }

    @Override
    public void addToFridge(List<FridgeFood> newFoods) {
        // TODO: Merge element avoiding duplicates
        this.foods.addAll(newFoods);
    }

    public List<FridgeFood> getFood() {
        return this.foods;
    }

    @Override
    public  List<FridgeFood> stringToFridgeFood(String content) {
        try {
            return Arrays.asList(this.mapper.readValue(content, FridgeFood[].class));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO Add return type of add exception
        return null;
    }

    public static void main(String[] args) throws IOException {
        Fridge f = Fridge.getInstance();
        f.readFridge("fridge2.json");
        List<FridgeFood> list = f.readFile("fridge4.json");
        f.addToFridge(list);
        f.writeFridge("fridge5.json");
        f.getFood().forEach(System.out::println);
    }
}
