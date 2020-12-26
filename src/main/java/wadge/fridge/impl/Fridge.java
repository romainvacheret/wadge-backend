package wadge.fridge.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wadge.fridge.api.IDataManager;
import wadge.fridge.api.IFridge;

public class Fridge implements IFridge {
    private List<FridgeFood> foods;
    private final IDataManager manager;
    private static Fridge instance;

    private Fridge() {
        this.manager = DataManager.getInstance();
        this.foods = new ArrayList<>();
    }

    public static Fridge getInstance() {
        if (instance == null) {
            instance = new Fridge();
        }
        return instance;
    }

    @Override
    public void readFridge(String fileName) {
        this.foods = this.manager.readFile(fileName);
    }

    @Override
    public void writeFridge(String fileName) throws IOException {
        this.manager.writeFile(this.foods, fileName);
    }

    @Override
    public void addToFridge(List<FridgeFood> newFoods) {
        // TODO: Merge element avoiding duplicates
        this.foods.addAll(newFoods);
    }

    public List<FridgeFood> getFood() {
        return this.foods;
    }
}
