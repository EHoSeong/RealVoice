package MongoDB.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import MongoDB.service.UserVoiceService;
import MongoDB.vo.UserVO;

@CrossOrigin(origins = "*")
@RestController
public class UserVoiceController {

	@Autowired
	private UserVoiceService userVoiceService;

	@PostMapping("/user/voice/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> requestBody) {
		String userUuid = requestBody.get("userUuid");
		String callingCode = requestBody.get("callingCode");
		String phoneNumber = requestBody.get("phoneNumber");
		String nickName = requestBody.get("nickName");

		UserVO userVO = new UserVO(userUuid, callingCode, phoneNumber, nickName);
		userVoiceService.saveUser(userVO);
		userVoiceService.saveAdminUser(userVO);
		userVoiceService.saveAllUser(userVO);

		System.out.println("탐!!!");
		return ResponseEntity.ok("Registration successful");
	}

	// 전화번호로 User DB 조회하는 함수
	@GetMapping("/user/phoneAuth/{phoneNumber}")
	public String login(@PathVariable String phoneNumber) {
		UserVO userVO = userVoiceService.getUserByPhoneNumber(phoneNumber);
		if (userVO == null) {
			// 프론트에 던져줄 메세지
			return "가입되지 않은 번호입니다";
		}
		return userVO.getUserUuid();
	}

	// 친구삭제
	@PostMapping("/friends/remove")
	public String deleteUserByUuid(@RequestBody Map<String, String> requestBody) {
		String uuid = requestBody.get("userUuid");
		userVoiceService.deleteUserByUuid(uuid);
		userVoiceService.deleteCollectionByUuid(uuid);
		return "삭제가 완료되었습니다.";
	}

	// 친구추가
	@PostMapping("/friends/addUser")
	public String addUser(@RequestBody Map<String, String> requestBody) {
		String requestUuid = requestBody.get("requestUuid");
		String targetUuid = requestBody.get("targetUuid");

		UserVO requestUser = userVoiceService.getUserByUuid(requestUuid);
		UserVO targetUser = userVoiceService.getUserByUuid(targetUuid);

		userVoiceService.saveUserbyUuid(targetUser, requestUuid);
		userVoiceService.saveUserbyUuid(requestUser, targetUuid);
		return "친구추가가 완료되었습니다.";
	}

	// uuid로 사용자 프로필 조회
	@GetMapping("/user/profile/{uuid}")
	public String findUser(@PathVariable Map<String, String> requestBody) {
		UserVO userVO = userVoiceService.findUserByUuid("userInfo");
		JsonObject res = new JsonObject();
		res.addProperty("phoneNumber", userVO.getPhoneNumber());
		res.addProperty("nickName", userVO.getNickName());
		res.addProperty("uuid", userVO.getUserUuid());

		return res.toString();
	}

	@PostMapping("/audio/upload")
	public String recordVoice(@RequestParam("file") MultipartFile file) {
		try {
			userVoiceService.storeAudio(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "업로드가 완료되었습니다";
	}
}