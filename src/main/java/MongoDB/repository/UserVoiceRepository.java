package MongoDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import MongoDB.vo.UserVO;

public interface UserVoiceRepository extends MongoRepository<UserVO, String> {
	// 유저 정보 저장
//	UserVO save(UserVO user);

//	UserVO makeUserCollection(String uuid);
	
	UserVO findByPhoneNumber(String phoneNumber);
	
	UserVO findByPhoneNumberAndCollectionName(String phoneNumber, String collectionName);
}