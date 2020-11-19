package com.sbs.example.ucong.dto;

import java.util.Map;

public class Member {
	public int id;
	public String loginId;
	public String loginPw;
	public String name;
	public String regDate;
	public String updateDate;
	

	public boolean isAdmin() {
		return loginId.equals("ucong");
	}
	
	public Member(Map<String, Object> memberMap) {
		this.id =(int)memberMap.get("id");
		this.loginId=(String)memberMap.get("loginId");
		this.loginPw=(String)memberMap.get("loginPw");
		this.name=(String)memberMap.get("name");
		this.regDate=(String)memberMap.get("regDate");
		this.updateDate=(String)memberMap.get("updateDate");
	}
}
