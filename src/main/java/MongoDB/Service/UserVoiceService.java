package MongoDB.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MongoDB.repository.UserVoiceRepository;
import MongoDB.vo.UserVO;

@Service
public class UserVoiceService {
	@Autowired
	private UserVoiceRepository userVoiceRepository;

	
	public void saveUser(UserVO user) {
        userVoiceRepository.save(user);
    }
	
	public UserVO getUserByPhoneNumber(String phoneNumber, String collectionName) {
		return userVoiceRepository.findByPhoneNumber(phoneNumber, collectionName);
	}
	public UserVO findUserInfo(String phoneNumber) {
		return userVoiceRepository.findUserInfo(phoneNumber);
	}
//	public String makeUserCollection(String uuid) {
//		return userVoiceRepository.makeUserCollection(uuid);
//	}
//	@Autowired
//	private MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//	private final UserVoiceRepository userVoiceRepository;
//
//	public UserVoiceService(UserVoiceRepository userVoiceRepository) {
//		this.userVoiceRepository = userVoiceRepository;
//	}
//
//	public void saveUserInfo(String nickName, String email, String password) {
//		UserVO user = new UserVO(nickName, email, password);
//		userVoiceRepository.save(user);
//	}
//	public void saveUserVoice(String userId, MultipartFile voiceFile) throws IOException {
	// 사용자 ID로 사용자 정보 조회
//		User user = userRepository.findById(userId).orElse(null);
//		if (user != null) {
//			// 파일 저장 경로 설정
//			String uploadDir = "user-voices"; // 파일을 저장할 디렉토리 설정
//			String fileName = StringUtils.cleanPath(voiceFile.getOriginalFilename());
//			Path uploadPath = Paths.get(uploadDir);
//
//			// 디렉토리가 없을 경우 생성
//			if (!Files.exists(uploadPath)) {
//				Files.createDirectories(uploadPath);
//			}
//
//			// 파일 저장
//			Path filePath = uploadPath.resolve(fileName).normalize();
//			Files.copy(voiceFile.getInputStream(), filePath);
//
//			// 파일 경로로 음성 데이터 저장
//			UserVoice userVoice = new UserVoice(user.getId(), filePath.toString());
//			userVoiceRepository.save(userVoice);
//		} else {
//			// 사용자 정보가 없을 경우 처리 (예외 처리 등)
//			System.out.println("User not found for ID: " + userId);
//		}
//	}

//	public UserVO getUserVoiceById(String userVoiceId) {
//		return userVoiceRepository.getUserById(userVoiceId);
//	}

//	public void deleteUserVoice(String userVoiceId) {
//		// 음성 데이터 ID로 음성 데이터 조회
//		UserVO userVoice = userVoiceRepository.findById(userVoiceId).orElse(null);
//		if (userVoice != null) {
//			// 음성 파일 삭제
//			String filePath = userVoice.getFilePath();
//			if (filePath != null) {
//				try {
//					Files.deleteIfExists(Paths.get(filePath));
//				} catch (IOException e) {
//					e.printStackTrace(); // 파일 삭제 중 오류 발생 시 로그 출력
//				}
//			}
//
//			// 음성 데이터 삭제
//			userVoiceRepository.delete(userVoice);
//		} else {
//			System.out.println("User voice not found for ID: " + userVoiceId);
//		}
//	}

//	public void test() {
//		// 데이터베이스 선택
//		MongoDatabase database = mongoClient.getDatabase("mydatabase");
//		System.out.println(mongoClient.getDatabase("Test").getCollection("test").find().first().get("id"));
//
//		// 컬렉션 선택
//		MongoCollection<Document> collection = database.getCollection("mycollection");
//
//		// 쿼리 실행
//		Document query = new Document("name", "John");
//		Document result = collection.find(query).first();
//
//		// 결과 처리
////			if (result != null) {
////				System.out.println("Found document: " + result.toJson());
////			} else {
////				System.out.println("Document not found");
////			}
//	}
}