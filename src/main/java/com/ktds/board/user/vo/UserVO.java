package com.ktds.board.user.vo;

public class UserVO {

	private String userId;
	private String userEmail;
	private String userNickname;
	private String userPassword;
	private int points;
	private String latestPasswordModifyDate;
	private String createdDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getLatestPasswordModifyDate() {
		return latestPasswordModifyDate;
	}

	public void setLatestPasswordModifyDate(String latestPasswordModifyDate) {
		this.latestPasswordModifyDate = latestPasswordModifyDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
