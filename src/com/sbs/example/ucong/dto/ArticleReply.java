package com.sbs.example.ucong.dto;

import java.util.Map;

public class ArticleReply {
	public int id;
	public int memberId;
	public int articleId;
	public String regDate;
	public String body;
	public ArticleReply(Map<String, Object> articleReplyMap) {
		this.id = (int)articleReplyMap.get("id");
		this.memberId = (int)articleReplyMap.get("memberId");
		this.articleId = (int)articleReplyMap.get("articleId");
		this.regDate = (String)articleReplyMap.get("regDate");
		this.body = (String)articleReplyMap.get("body");
		
	}
	
}
