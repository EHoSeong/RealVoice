package MongoDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import MongoDB.vo.UserVO;

public interface UserVoiceRepository extends MongoRepository<UserVO, String> {
	// 유저 정보 저장
	UserVO save(UserVO user);

	String makeUserCollection(String uuid);
	
	UserVO findUserInfo(String phoneNumber);
	
	UserVO findByPhoneNumber(String phoneNumber, String collectionName);
}