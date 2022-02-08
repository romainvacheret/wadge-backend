package wadge.utils.db;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

	public <T> List<T> readFile(final String filename, final Class<T> classType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(
				Paths.get(filename).toFile(), 
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, classType));
		} catch (IOException e) {
			e.printStackTrace();
			return List.of();
		}
	}
}
