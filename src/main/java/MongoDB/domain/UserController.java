//package com.example.RealVoice_Backend.domain.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody PhoneNumberDTO phoneNumberDTO) {
//        // 전화번호를 이용한 사용자 인증 및 회원가입
//        String phoneNumber = phoneNumberDTO.getPhoneNumber();
//        String verificationCode = userService.generateVerificationCode();
//        if (userService.verifyPhoneNumber(phoneNumber, verificationCode)) {
//            User user = new User();
//            user.setPhoneNumber(phoneNumber);
//            user.setVerificationCode(verificationCode);
//            userService.registerUser(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification code is invalid.");
//        }
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPhoneNumber(@RequestParam String phoneNumber) {
//        userService.sendVerificationCode(phoneNumber);
//        return ResponseEntity.ok("Verification code sent to " + phoneNumber);
//    }
//}

