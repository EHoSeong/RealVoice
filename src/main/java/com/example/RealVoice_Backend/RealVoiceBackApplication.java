package com.example.RealVoice_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "MongoDB.repository")
@SpringBootApplication
@ComponentScan(basePackages = { "com.example.RealVoice_Backend" })
public class RealVoiceBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealVoiceBackApplication.class, args);
//		UserVoiceService test = new UserVoiceService();
//		test.saveUserInfo("TestNickName", "test@naver.com", "1111");
	}

}
