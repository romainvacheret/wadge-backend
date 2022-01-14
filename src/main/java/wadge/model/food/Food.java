package wadge.model.food;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private @Id long id;
    private String name;
    private String type;
    private List<java.time.Month> availability;
    private int days;
    @EqualsAndHashCode.Exclude
    private int weight;
    @EqualsAndHashCode.Exclude
    private boolean bulk;
}