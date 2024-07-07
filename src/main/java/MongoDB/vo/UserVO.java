package MongoDB.vo;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "userInfo") // 실제 몽고 DB 컬렉션 이름
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

	private String userUuid;
	private String callingCode;
	private String phoneNumber;
	private String nickName;
	private String realName;
	private String countryName;
	private String bio;
	private String joinYear;

	public UserVO(String userUuid, String callingCode, String phoneNumber, String nickName, String realName,
			String countryName, String bio, String joinYear) {
		this.userUuid = userUuid;
		this.callingCode = callingCode;
		this.phoneNumber = phoneNumber;
		this.nickName = nickName;
		this.realName = realName;
		this.countryName = countryName;
		this.bio = bio;
		this.joinYear = joinYear;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getCallingCode() {
		return callingCode;
	}

	public void setCallingCode(String callingCode) {
		this.callingCode = callingCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCollectionName() {
		return nickName;
	}

	public void setCollectionName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getJoinYear() {
		return joinYear;
	}

	public void setJoinYear(String joinYear) {
		this.joinYear = joinYear;
	}
}
