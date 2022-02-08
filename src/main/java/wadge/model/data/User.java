package wadge.model.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private @Id long id;
    private ProfileCategory category;
    private List<ScoredRecipe> recipes;
    private String name;

    enum ProfileCategory {
        VEGAN, SPORTY, DELBOT, YOUTH
    }
}
