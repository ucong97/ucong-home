package com.sbs.example.ucong.dto;

import java.util.Map;

public class Member {
	public int id;
	public String loginId;
	public String loginPw;
	public String name;
	public Member(int id,String memberLoginId, String memberLoginPw, String memberName) {
		this.id = id;
		this.loginId=memberLoginId;
		this.loginPw=memberLoginPw;
		this.name=memberName;
	}
	public Member(Map<String, Object> memberMap) {
		this.id =(int)memberMap.get("id");
		this.loginId=(String)memberMap.get("loginId");
		this.loginPw=(String)memberMap.get("loginPw");
		this.name=(String)memberMap.get("name");
	}
}
