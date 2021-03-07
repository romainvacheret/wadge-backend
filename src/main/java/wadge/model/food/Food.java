package wadge.model.food;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Food {
    private String name;
    private String type;
    private List<Month> availability;
    private int days;
    private int weight;
    private boolean bulk;

    // Needed by com.fastxml.jackson
    public Food() {}

    public Food(String name, String type, Month[] availability, int days, int weight, boolean bulk) {
        this.name = name;
        this.type = type;
        this.availability = Arrays.asList(availability);
        this.days = days;
        this.weight = weight;
        this.bulk = bulk;
    }

    public String getName() { return name; }

    public String getType() { return type; }

    public List<Month> getAvailability() { return availability; }

    public int getDays() { return days; }

    public int getWeight() { return weight; }

    public boolean getBulk() { return bulk; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((availability == null) ? 0 : availability.hashCode());
        result = prime * result + days;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return days == food.days && weight == food.weight && bulk == food.bulk && Objects.equals(name, food.name) && Objects.equals(type, food.type) && Objects.equals(availability, food.availability);
    }

    @Override
    public String toString() {
        return "Food [availability=" + availability + ", days=" + days + ", name=" + name + ", type=" + type + "]";
    }

}
