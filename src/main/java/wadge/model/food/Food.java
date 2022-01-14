package wadge.model.food;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private String name;
    private String type;
    private List<Month> availability;
    private int days;
    @EqualsAndHashCode.Exclude
    private int weight;
    @EqualsAndHashCode.Exclude
    private boolean bulk;
}