package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import wadge.model.data.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {}
