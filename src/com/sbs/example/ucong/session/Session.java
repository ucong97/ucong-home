package com.sbs.example.ucong.session;

public class Session {
	public int loginedMemberId;
	
	public Session() {
		loginedMemberId=0;
	}
	public void login(int id) {
		loginedMemberId = id;
	}
	public boolean logined() {
		return loginedMemberId>0;
	}

}
