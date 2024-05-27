package MongoDB.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import MongoDB.vo.UserVO;

@Repository
public interface UserRepository extends MongoRepository<UserVO, String> {
	UserVO getUserById(String id);
}