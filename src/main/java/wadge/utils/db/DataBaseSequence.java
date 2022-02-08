package wadge.utils.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="database_sequences")
public class DataBaseSequence {
    private @Id String id;
    private long sequence;
}