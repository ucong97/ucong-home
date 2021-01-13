package com.sbs.example.ucong.dto;

import java.util.Map;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
