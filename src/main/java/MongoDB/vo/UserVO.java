package MongoDB.vo;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserVO {
	private String userUuid;

	private String callingCode;

	private String phoneNumber;

	private String nickName;

	private String realName;

	private String countryName;

	private String bio;

	private String joinYear;

	private String createTime;

	private String userToken;

	private String fileId;

	private LocalDateTime timeStamp;

	public UserVO() {
	}

	public UserVO(String userUuid, String callingCode, String phoneNumber, String nickName, String realName,
			String countryName, String bio, String joinYear, String createTime, String userToken) {
		this.userUuid = userUuid;
		this.callingCode = callingCode;
		this.phoneNumber = phoneNumber;
		this.nickName = nickName;
		this.realName = realName;
		this.countryName = countryName;
		this.bio = bio;
		this.joinYear = joinYear;
		this.createTime = createTime;
		this.userToken = userToken;
	}

	public UserVO(String userUuid, String fileId, LocalDateTime timestamp) {
		this.userUuid = userUuid;
		this.fileId = fileId;
		this.timeStamp = timestamp;
	}

	public String getUserUuid() {
		return this.userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getCallingCode() {
		return this.callingCode;
	}

	public void setCallingCode(String callingCode) {
		this.callingCode = callingCode;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCollectionName() {
		return this.nickName;
	}

	public void setCollectionName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getJoinYear() {
		return this.joinYear;
	}

	public void setJoinYear(String joinYear) {
		this.joinYear = joinYear;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public LocalDateTime getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
}
