package MongoDB.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.model.GridFSFile;

import MongoDB.vo.UserVO;

@Service
public class UserVoiceService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private GridFsTemplate gridFsTemplate;

	private static final String UPLOAD_DIR = "/home/ec2-user/voice/";

	public void saveUser(UserVO user) {
		String collectionName = user.getUserUuid();
		this.mongoTemplate.save(user, collectionName);
	}

	public void saveUserbyUuid(UserVO user, String userUuid) {
		this.mongoTemplate.save(user, userUuid);
	}

	public void saveAdminUser(UserVO user) {
		this.mongoTemplate.save(user, "adminDB");
	}

	public void saveAllUser(UserVO user) {
		this.mongoTemplate.save(user, "userInfo");
	}

	public UserVO getUserByPhoneNumber(String phoneNumber) {
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("phoneNumber").is(phoneNumber));
		return (UserVO) this.mongoTemplate.findOne(query, UserVO.class, "userInfo");
	}

	public UserVO getUserByUuid(String userUuid) {
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("userUuid").is(userUuid));
		return (UserVO) this.mongoTemplate.findOne(query, UserVO.class, "userInfo");
	}

	public UserVO findUserByUuid(String userUuid) {
		String collectionName = userUuid;
		Query query = new Query();
		return (UserVO) this.mongoTemplate.findOne(query, UserVO.class, collectionName);
	}

	public UserVO checkNickName(String nickName) {
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("nickName").is(nickName));
		return (UserVO) this.mongoTemplate.findOne(query, UserVO.class, "userInfo");
	}

	public void deleteUserByUuid(String userUuid) {
		String collectionName = userUuid;
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("userUuid").is(userUuid));
		this.mongoTemplate.remove(query, UserVO.class, collectionName);
		this.mongoTemplate.remove(query, UserVO.class, "adminDB");
		this.mongoTemplate.remove(query, UserVO.class, "userInfo");
	}

	public void deleteFromAllCollections(String userUuid) {
		MongoDatabase database = this.mongoTemplate.getDb();
		for (MongoCursor<String> mongoCursor = database.listCollectionNames().iterator(); mongoCursor.hasNext();) {
			String collectionName = mongoCursor.next();
			Query query = new Query((CriteriaDefinition) Criteria.where("field").is(userUuid));
			this.mongoTemplate.remove(query, Object.class, collectionName);
		}
	}

	public void deleteCollectionByUuid(String userUuid) {
		this.mongoTemplate.dropCollection(userUuid);
	}

	public void updateInfo(String userUuid, String beforeValue, String afterValue, String change) {
		Query query = new Query((CriteriaDefinition) Criteria.where(change).is(beforeValue));
		Update update = new Update();
		update.set(change, afterValue);
		this.mongoTemplate.updateMulti(query, (UpdateDefinition) update, userUuid);
		this.mongoTemplate.updateMulti(query, (UpdateDefinition) update, "adminDB");
		this.mongoTemplate.updateMulti(query, (UpdateDefinition) update, "userInfo");
	}

	public void storeAudio(MultipartFile file, String uuid) throws IOException {
		String fileId, fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path uploadPath = Paths.get("/home/ec2-user/voice/" + uuid, new String[0]);
		if (!Files.exists(uploadPath, new java.nio.file.LinkOption[0]))
			Files.createDirectories(uploadPath, (FileAttribute<?>[]) new FileAttribute[0]);
		Path filePath = uploadPath.resolve(fileName);
		file.transferTo(filePath.toFile());
		InputStream inputStream = file.getInputStream();
		try {
			fileId = this.gridFsTemplate.store(inputStream, fileName).toString();
			if (inputStream != null)
				inputStream.close();
		} catch (Throwable throwable) {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			throw throwable;
		}
		LocalDateTime timestamp = LocalDateTime.now();
		UserVO voiceRecord = new UserVO(uuid, fileId, timestamp);
		this.mongoTemplate.save(voiceRecord, "Voice");
	}

	public Resource findVoiceFile(String uuid) throws IOException {
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("userUuid").is(uuid));
		Document document = (Document) this.mongoTemplate.findOne(query, Document.class, "Voice");
		GridFSFile gridFSFile = this.gridFsTemplate
				.findOne(Query.query((CriteriaDefinition) Criteria.where("_id").is(document.getString("fileId"))));
		if (gridFSFile != null) {
			GridFsResource resource = this.gridFsTemplate.getResource(gridFSFile);
			return (Resource) resource;
		}
		throw new IOException("File not found with ID: " + uuid);
	}
	
	public String getToken(String userUuid) {
		Query query = new Query();
		query.addCriteria((CriteriaDefinition) Criteria.where("userUuid").is(userUuid));
		UserVO userVO = this.mongoTemplate.findOne(query, UserVO.class, "userInfo");
		return userVO.getUserToken();
	}
}
