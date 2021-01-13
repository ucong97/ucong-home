package com.sbs.example.ucong.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Member {
	private int id;
	private String loginId;
	private String loginPw;
	private String name;
	private String regDate;
	private String updateDate;

	public String getType() {
		return isAdmin() ? "관리자" : "일반회원";
	}

	public boolean isAdmin() {
		return loginId.equals("ucong");
	}

	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
		this.regDate = (String) memberMap.get("regDate");
		this.updateDate = (String) memberMap.get("updateDate");
	}

}
