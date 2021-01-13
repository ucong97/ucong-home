package com.sbs.example.ucong.dto;

import java.util.Map;

import lombok.Data;

@Data
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

}
