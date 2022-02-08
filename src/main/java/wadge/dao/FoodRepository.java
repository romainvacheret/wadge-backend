package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import wadge.model.food.Food;

@Repository
public interface FoodRepository extends MongoRepository<Food, Long> {}
