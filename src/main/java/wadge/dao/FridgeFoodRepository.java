package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import wadge.model.fridge.FridgeFood;

@Repository
public interface FridgeFoodRepository extends MongoRepository<FridgeFood, Long> {}
