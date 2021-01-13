package com.sbs.example.ucong.dto;

import java.util.Map;

public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private int boardId;
	private int hitCount;
	private int likesCount;
	private int commentsCount;

	private String extra__memberName;
	private String extra__boardName;
	private String extra__boardCode;

	public Article(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updateDate = (String) map.get("updateDate");
		this.title = (String) map.get("title");
		this.body = (String) map.get("body");
		this.memberId = (int) map.get("memberId");
		this.boardId = (int) map.get("boardId");
		this.likesCount = (int) map.get("likesCount");
		this.commentsCount = (int) map.get("commentsCount");
		this.hitCount = (int) map.get("hitCount");

		if (map.containsKey("extra__memberName")) {
			this.extra__memberName = (String) map.get("extra__memberName");
		}
		if (map.containsKey("extra__boardCode")) {
			this.extra__boardCode = (String) map.get("extra__boardCode");
		}
		if (map.containsKey("extra__boardName")) {
			this.extra__boardName = (String) map.get("extra__boardName");
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getExtra__memberName() {
		return extra__memberName;
	}

	public void setExtra__memberName(String extra__memberName) {
		this.extra__memberName = extra__memberName;
	}

	public String getExtra__boardName() {
		return extra__boardName;
	}

	public void setExtra__boardName(String extra__boardName) {
		this.extra__boardName = extra__boardName;
	}

	public String getExtra__boardCode() {
		return extra__boardCode;
	}

	public void setExtra__boardCode(String extra__boardCode) {
		this.extra__boardCode = extra__boardCode;
	}

}
