package MongoDB.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import MongoDB.vo.UserVO;

@Service
public class UserVoiceService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private GridFsTemplate gridFsTemplate;
	private static final String UPLOAD_DIR = "C:\\test\\";

	// 각 uuid별 컬렉션 저장
	public void saveUser(UserVO user) {
		String collectionName = user.getUserUuid();
		mongoTemplate.save(user, collectionName);
	}

	public void saveUserbyUuid(UserVO user, String userUuid) {
		mongoTemplate.save(user, userUuid);
	}

	// adminDB
	public void saveAdminUser(UserVO user) {
		mongoTemplate.save(user, "adminDB");
	}

	// 전체 user DB
	public void saveAllUser(UserVO user) {
		mongoTemplate.save(user, "userInfo");
	}

	public UserVO getUserByPhoneNumber(String phoneNumber) {
		Query query = new Query();
		query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
		return mongoTemplate.findOne(query, UserVO.class, "userInfo");
	}

	public UserVO getUserByUuid(String userUuid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userUuid").is(userUuid));
		return mongoTemplate.findOne(query, UserVO.class, "userInfo");
	}

	public UserVO findUserByUuid(String userUuid) {
		String collectionName = userUuid;
		Query query = new Query();
		return mongoTemplate.findOne(query, UserVO.class, collectionName);
	}

	// UUID를 사용하여 사용자의 정보를 삭제하는 메서드 추가
	public void deleteUserByUuid(String userUuid) {
		String collectionName = userUuid;
		Query query = new Query();
		query.addCriteria(Criteria.where("userUuid").is(userUuid));
		mongoTemplate.remove(query, UserVO.class, collectionName);
		mongoTemplate.remove(query, UserVO.class, "adminDB");
		mongoTemplate.remove(query, UserVO.class, "userInfo");
	}

	// 컬렉션 삭제
	public void deleteCollectionByUuid(String userUuid) {
		mongoTemplate.dropCollection(userUuid);
	}

	public String storeAudio(MultipartFile file) throws IOException {

		String fileId = UUID.randomUUID().toString();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get(UPLOAD_DIR + fileId);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Path filePath = uploadPath.resolve(fileName);
		file.transferTo(filePath.toFile());

		return fileId;
	}

}