package com.bkeryah.bean;

public class UserStatisticsClass {
	int UserId;
	String UserName;
	int SentCommentsCount;
	int RecievedCommentsCount;
	int CreatedLettersCount;
	int CreatedRecordsCount;
	public int getSentCommentsCount() {
		return SentCommentsCount;
	}
	public void setSentCommentsCount(int sentCommentsCount) {
		SentCommentsCount = sentCommentsCount;
	}
	public int getRecievedCommentsCount() {
		return RecievedCommentsCount;
	}
	public void setRecievedCommentsCount(int recievedCommentsCount) {
		RecievedCommentsCount = recievedCommentsCount;
	}
	public int getCreatedLettersCount() {
		return CreatedLettersCount;
	}
	public void setCreatedLettersCount(int createdLettersCount) {
		CreatedLettersCount = createdLettersCount;
	}
	public int getCreatedRecordsCount() {
		return CreatedRecordsCount;
	}
	public void setCreatedRecordsCount(int createdRecordsCount) {
		CreatedRecordsCount = createdRecordsCount;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
}
