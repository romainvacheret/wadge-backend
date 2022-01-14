package wadge.utils.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import wadge.dao.FoodRepository;
import wadge.model.food.LoadedFood;
import wadge.utils.db.JsonReader;
import wadge.utils.db.SequenceGenerator;

@Configuration
@AllArgsConstructor
public class FoodConfig {
    private SequenceGenerator sequenceGenerator;
    
    @Bean
    CommandLineRunner foodCommandLineRunner(final FoodRepository foodRepository) {
        return args -> {
            if(foodRepository.findAll().isEmpty()) {
                foodRepository.saveAll( 
                    new JsonReader().readFile("food_list.json", LoadedFood.class)
                        .stream()
                        .map(food -> food.toFood(sequenceGenerator.generateSequence("food_sequence")))
                        .toList());
            }
        };
    }
}
