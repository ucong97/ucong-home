package com.sbs.example.ucong.dto;

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
}
