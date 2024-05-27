package MongoDB.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users") // 실제 몽고 DB 컬렉션 이름 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
    private String id; // 여기서 id는 mongoDB에서 객체를 식별하기 위한 id
    private String nickName; // 닉네임
    private String userName; // 이름
    private String phoneNumber; //전화번호
    private String url; // 프로필 사진 URL
    private String verificationCode; // 인증 코드
    private Date created_at; // 가입 날짜
    public User(String id) {this.id = id;}
}
