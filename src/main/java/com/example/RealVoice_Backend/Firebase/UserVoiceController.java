package com.example.RealVoice_Backend.Firebase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MongoDB.repository.UserVoiceRepository;
import MongoDB.vo.UserVO;

@Service
@RestController
public class UserVoiceController {

	@Autowired
	private UserVoiceRepository userVoiceRepository;

	@CrossOrigin(origins = "*")
	@PostMapping("/user/voice/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> requestBody) {
		String callingCode = requestBody.get("callingCode");
		String phoneNumber = requestBody.get("phoneNumber");
		String nickName = requestBody.get("nickName");
		
		UserVO userVO = new UserVO(callingCode, phoneNumber, nickName);
		userVoiceRepository.save(userVO);
		System.out.println("탐!!!");
		return ResponseEntity.ok("Registration successful");
	}

	@GetMapping("/user/voice/{phoneNumber}")
	public ResponseEntity<UserVO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
		UserVO userVO = userVoiceRepository.findByPhoneNumber(phoneNumber);
		if (userVO != null) {
			return ResponseEntity.ok(userVO);
		} else {
			return ResponseEntity.notFound().build();
		}
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