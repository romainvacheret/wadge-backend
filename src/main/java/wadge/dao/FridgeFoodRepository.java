package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import wadge.model.fridge.FridgeFood;

public interface FridgeFoodRepository extends MongoRepository<FridgeFood, Long> {}
