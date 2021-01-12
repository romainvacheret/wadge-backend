package wadge.model.food;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Month {
    JANUARY("janvier"), FEBRUARY("fevrier"), MARCH("mars"), APRIL("avril"), MAY("mai"), JUN("juin"), JULY("juillet"), 
    AUGUST("aout"), SEPTEMBER("septembre"), OCTOBER("octobre"), NOVEMBER("novembre"), DECEMBER("decembre");
    
    private final String value;
    static final Map<String, Month> BY_VALUE = new HashMap<>();

    static {
        Arrays.asList(values()).stream().forEach(month -> BY_VALUE.put(month.value, month));
    }

    Month(String value) { 
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public Month of(String value) {
        return BY_VALUE.get(value);
    }

}
