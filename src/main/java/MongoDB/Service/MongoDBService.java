package MongoDB.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MongoDB.domain.UserRepository;
import MongoDB.vo.UserVO;

@Service
public class MongoDBService {

//	private final UserRepository userRepository;
//
//	public MongoDBService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	public UserVO getUserById(String id) {
//		return userRepository.findById(id).orElse(null);
//	}
}