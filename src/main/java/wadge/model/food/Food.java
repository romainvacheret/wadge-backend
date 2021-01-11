package wadge.model.food;

import java.util.Arrays;
import java.util.List;

public class Food {
    private String name;
    private String type;
    private List<String> availability;
    private int days;

    // Needed by com.fastxml.jackson
    public Food() {}

    public Food(String name, String type, String[] availability, int days) {
        this.name = name;
        this.type = type;
        this.availability = Arrays.asList(availability);
        this.days = days;
    }

    public String getName() { return name; }

    public String getType() { return type; }

    public List<String> getAvailability() { return availability; }

    public int getDays() { return days; }

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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Food other = (Food) obj;
        if (availability == null) {
            if (other.availability != null)
                return false;
        } else if (!availability.equals(other.availability))
            return false;
        if (days != other.days)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Food [availability=" + availability + ", days=" + days + ", name=" + name + ", type=" + type + "]";
    }

}
