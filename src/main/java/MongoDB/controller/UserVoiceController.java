package MongoDB.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.gson.JsonObject;

import Firebase.Firebase;
import MongoDB.service.UserVoiceService;
import MongoDB.vo.UserVO;
import Properties.RealVoice;

@CrossOrigin(origins = { "*" })
@RestController
public class UserVoiceController {
	@Autowired
	private UserVoiceService userVoiceService;

	@GetMapping({ "/health" })
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity("OK", (HttpStatusCode) HttpStatus.OK);
	}

	@PostMapping({ "/user/voice/register" })
	public ResponseEntity<String> register(@RequestBody Map<String, String> requestBody) {
		String userUuid = requestBody.get("userUuid");
		String callingCode = requestBody.get("callingCode");
		String phoneNumber = requestBody.get("phoneNumber");
		String nickName = requestBody.get("nickName");
		String realName = requestBody.get("realName");
		String countryName = requestBody.get("countryName");
		String bio = requestBody.get("bio");
		String joinYear = requestBody.get("joinYear");
		String createTime = requestBody.get("createTime");
		String userToken = requestBody.get("userToken");
		
		UserVO userVO = new UserVO(userUuid, callingCode, phoneNumber, nickName, realName, countryName, bio, joinYear,
				createTime, userToken);
		this.userVoiceService.saveUser(userVO);
		this.userVoiceService.saveAdminUser(userVO);
		this.userVoiceService.saveAllUser(userVO);
		return ResponseEntity.ok("Registration successful");
	}

	@GetMapping({ "/user/phoneAuth/{phoneNumber}" })
	public String login(@PathVariable String phoneNumber) {
		UserVO userVO = this.userVoiceService.getUserByPhoneNumber(phoneNumber);
		if (userVO == null)
			return "";
		return userVO.getUserUuid();
	}

	@PostMapping({ "/friends/addUser" })
	public String addUser(@RequestBody Map<String, String> requestBody) {
		String requestUuid = requestBody.get("requestUuid");
		String targetUuid = requestBody.get("targetUuid");
		UserVO requestUser = this.userVoiceService.getUserByUuid(requestUuid);
		UserVO targetUser = this.userVoiceService.getUserByUuid(targetUuid);
		this.userVoiceService.saveUserbyUuid(targetUser, requestUuid);
		this.userVoiceService.saveUserbyUuid(requestUser, targetUuid);
		return "";
	}

	@PostMapping({ "/friends/remove" })
	public String deleteUserByUuid(@RequestBody Map<String, String> requestBody) {
		String uuid = requestBody.get("userUuid");
		UserVO userVO = this.userVoiceService.getUserByUuid(uuid);
		if (userVO != null) {
			this.userVoiceService.deleteUserByUuid(uuid);
			this.userVoiceService.deleteFromAllCollections(uuid);
			return "";
		}
		return "";
	}

	@PostMapping({ "/user/remove" })
	public String removeUserByUuid(@RequestBody Map<String, String> requestBody) {
		String uuid = requestBody.get("userUuid");
		UserVO userVO = this.userVoiceService.getUserByUuid(uuid);
		if (userVO != null) {
			this.userVoiceService.deleteUserByUuid(uuid);
			this.userVoiceService.deleteCollectionByUuid(uuid);
			this.userVoiceService.deleteFromAllCollections(uuid);
		}
		return "";
	}

	@GetMapping({ "/user/profile/{uuid}" })
	public String findUser(@PathVariable String uuid) {
		UserVO userVO = this.userVoiceService.findUserByUuid(uuid);
		if (userVO != null) {
			JsonObject res = new JsonObject();
			res.addProperty("phoneNumber", userVO.getPhoneNumber());
			res.addProperty("nickName", userVO.getNickName());
			res.addProperty("userUuid", userVO.getUserUuid());
			return res.toString();
		}
		return "";
	}

	@GetMapping({ "/user/nickname/{nickName}" })
	public String checkNickName(@PathVariable String nickName) {
		UserVO userVO = this.userVoiceService.checkNickName(nickName);
		if (userVO == null)
			return "";
		return "";
	}

	@PostMapping({ "/user/profile/update" })
	public String updateNickName(@RequestBody Map<String, String> requestBody) {
		String uuid = requestBody.get("userUuid");
		String beforeValue = requestBody.get("beforeValue");
		String afterValue = requestBody.get("afterValue");
		String type = requestBody.get("type");
		if (type.equals("nickName")) {
			UserVO userVO = this.userVoiceService.checkNickName(afterValue);
			if (userVO == null) {
				this.userVoiceService.updateInfo(uuid, beforeValue, afterValue, type);
				return "";
			}
			return "";
		}
		this.userVoiceService.updateInfo(uuid, beforeValue, afterValue, type);
		return "";
	}

	@GetMapping({ "/play/{uuid}" })
	public ResponseEntity<InputStreamResource> play(@PathVariable String uuid) {
		try {
			Resource resource = this.userVoiceService.findVoiceFile(uuid);
			StreamingResponseBody stream = outputStream -> {
				try {
					InputStream inputStream = resource.getInputStream();
					try {
						byte[] buffer = new byte[1024];
						int bytesRead;
						while ((bytesRead = inputStream.read(buffer)) != -1)
							outputStream.write(buffer, 0, bytesRead);
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			InputStreamResource inResource = new InputStreamResource(resource.getInputStream());
			return ((ResponseEntity.BodyBuilder) ((ResponseEntity.BodyBuilder) ResponseEntity.ok()
					.header("Content-Type", new String[] { "audio/mp3" }))
					.header("Content-Disposition", new String[] { "attachment; filename=audio.mp3" })).body(inResource);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status((HttpStatusCode) HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping({ "/firebase/send" })
	public ResponseEntity<String> sendFirebase(@RequestBody Map<String, Object> notificationData) {
	    try {
	        // 요청 데이터에서 알림 제목, 본문, 토픽, 사용자 UUID 추출
	        String title = (String) notificationData.get("title");
	        String body = (String) notificationData.get("body");
	        String topic = (String) notificationData.get("topic");
	        String userUuid = (String) notificationData.get("userUuid");

	        String userToken = userVoiceService.getToken(userUuid);
	        // Firebase 객체 생성
	        Firebase firebase = new Firebase();
	        // 알림 전송 메서드 호출
	        return firebase.sendNotification(title, body, topic, userToken);
	    } catch (Exception e) {
	        // 기타 모든 예외 처리
	        return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping({ "/audio/upload" })
	public String recordVoice(@RequestParam("file") MultipartFile file, @RequestParam("uuid") String uuid) {
		try {
			this.userVoiceService.storeAudio(file, uuid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
