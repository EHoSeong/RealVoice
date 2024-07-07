package MongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "MongoDB.repository")
@SpringBootApplication
@ComponentScan(basePackages = { "MongoDB.controller", "MongoDB.service", "MongoDB.vo" })
public class RealVoiceBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealVoiceBackApplication.class, args);
	}

}
