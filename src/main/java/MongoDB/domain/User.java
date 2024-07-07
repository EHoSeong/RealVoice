//package MongoDB.domain;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Document(collection = "test") // 실제 몽고 DB 컬렉션 이름
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
////	private String userId;
//	@Id
//	private String phoneNumber;
//
//	public User(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//}