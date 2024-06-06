package MongoDB.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import MongoDB.Service.UserVoiceService;
import MongoDB.vo.UserVO;

@RestController
public class UserVoiceController {

	@Autowired
	private UserVoiceService userVoiceService;

	@CrossOrigin(origins = "*")
	@PostMapping("/user/voice/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> requestBody) {
		String userUuid = requestBody.get("userUuid");
		String callingCode = requestBody.get("callingCode");
		String phoneNumber = requestBody.get("phoneNumber");
		String nickName = requestBody.get("nickName");

		UserVO userVO = new UserVO(userUuid, callingCode, phoneNumber, nickName);
		userVoiceService.saveUser(userVO);
		System.out.println("탐!!!");
		return ResponseEntity.ok("Registration successful");
	}

	// 전화번호로 User DB 조회하는 함수
	@GetMapping("/user/voice/{phoneNumber}")
	public ResponseEntity<UserVO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
		UserVO userVO = userVoiceService.findUserInfo(phoneNumber);
		if (userVO != null) {
			return ResponseEntity.ok(userVO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/user/phoneAuth")
	public String login(@RequestBody Map<String, String> requestBody) {
		String phoneNumber = requestBody.get("phoneNumber");
		String uuid = requestBody.get("uuid");
		UserVO userVO = userVoiceService.findUserInfo(phoneNumber);
		if (userVO == null) {
			// 프론트에 던져줄 메세지
			return "가입되지 않은 번호입니다";
		}
		return userVO.getUserUuid();
	}

	@GetMapping("/user/profile")
	public String getProfile(@RequestBody Map<String, String> requestBody) {
		String phoneNumber = requestBody.get("phoneNumber");
		UserVO userVO = userVoiceService.findUserInfo(phoneNumber);
		JsonObject res = new JsonObject();
		res.addProperty("phoneNumber", userVO.getPhoneNumber());
		res.addProperty("nickName", userVO.getNickName());
		res.addProperty("uuid", userVO.getUserUuid());

		return res.toString();
	}

//	@GetMapping("/{userVoiceId}")
//	public ResponseEntity<UserVO> getUserVoiceById(@PathVariable String userVoiceId) {
//		UserVO userVoice = userVoiceService.getUserVoiceById(userVoiceId);
//		if (userVoice != null) {
//			return ResponseEntity.ok(userVoice);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

//	@DeleteMapping("/{userVoiceId}")
//	public ResponseEntity<String> deleteUserVoice(@PathVariable String userVoiceId) {
//		userVoiceService.deleteUserVoice(userVoiceId);
//		return ResponseEntity.ok("User voice deleted successfully.");
//	}

}