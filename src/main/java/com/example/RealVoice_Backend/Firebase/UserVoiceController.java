package com.example.RealVoice_Backend.Firebase;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class UserVoiceController {

//	@Autowired
//	private UserVoiceService userVoiceService;

	@CrossOrigin(origins = "*")
	@PostMapping("/user/voice/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> requestBody) {
		String userId = requestBody.get("userId");
		// userId로 등록 로직 구현
		System.out.println("탐!!!");
		return ResponseEntity.ok("Registration successful");
	}

	@GetMapping("/tt")
	public void test() {
		System.out.println("OK");
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