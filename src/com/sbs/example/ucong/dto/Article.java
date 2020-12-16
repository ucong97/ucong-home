package com.sbs.example.ucong.dto;

import java.util.Map;

public class Article {
	public int id;
	public String regDate;
	public String updateDate;
	public String title;
	public String body;
	public int memberId;
	public int boardId;
	public int hit;
	public String extra__memberName;
	public String extra__boardName;
	public String extra__boardCode;
	

	public Article(Map<String, Object> map) {
		this.id=(int)map.get("id");
		this.regDate=(String)map.get("regDate");
		this.updateDate=(String)map.get("updateDate");
		this.title=(String)map.get("title");
		this.body=(String)map.get("body");
		this.memberId=(int)map.get("memberId");
		this.boardId=(int)map.get("boardId");
		if(map.containsKey("extra__memberName")) {
			this.extra__memberName = (String)map.get("extra__memberName");
		}
		if(map.containsKey("extra__boardCode")) {
			this.extra__boardCode = (String)map.get("extra__boardCode");
		}
		if(map.containsKey("hit")) {
			this.hit = (int)map.get("hit");
		}
		
	}
}
