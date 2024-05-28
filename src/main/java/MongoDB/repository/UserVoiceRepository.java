package MongoDB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import MongoDB.vo.UserVO;

public interface UserVoiceRepository extends MongoRepository<UserVO, String> {
	// 사용자 이름으로 검색하기 위한 메서드
	UserVO save(UserVO user);

	UserVO findByPhoneNumber(String phoneNumber);
}