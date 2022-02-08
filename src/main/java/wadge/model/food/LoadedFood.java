package wadge.model.food;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wadge.utils.common.MonthMapper;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoadedFood {
	private String name;
    private String type;
    private List<String> availability;
    private int days;
    private int weight;
    private boolean bulk;	

	public Food toFood(final long id) {
		final MonthMapper mapper = new MonthMapper();
		return toFood(id, mapper);
	}

	public Food toFood(final long id, final MonthMapper mapper) {
		return Food.builder()
			.id(id)
			.name(name)
			.type(type)
			.availability(availability.stream().map(mapper::fromFrench).toList())
			.days(days)
			.weight(weight)
			.bulk(bulk)
			.build();
	}
}