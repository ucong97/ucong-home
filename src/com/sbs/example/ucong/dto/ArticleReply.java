package com.sbs.example.ucong.dto;

import java.util.Map;

public class ArticleReply {
	private int id;
	private int memberId;
	private int articleId;
	private String regDate;
	private String body;

	public ArticleReply(Map<String, Object> articleReplyMap) {
		this.id = (int) articleReplyMap.get("id");
		this.memberId = (int) articleReplyMap.get("memberId");
		this.articleId = (int) articleReplyMap.get("articleId");
		this.regDate = (String) articleReplyMap.get("regDate");
		this.body = (String) articleReplyMap.get("body");

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
