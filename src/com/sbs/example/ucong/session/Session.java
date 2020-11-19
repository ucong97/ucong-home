package com.sbs.example.ucong.session;

public class Session {
	public int loginedMemberId;

	public String getCurrentBoardCode;
	
	public Session() {
		loginedMemberId=0;
		getCurrentBoardCode = "notice";
	}
	public void login(int id) {
		loginedMemberId = id;
	}
	public boolean logined() {
		return loginedMemberId>0;
	}
	public boolean logouted() {
		return loginedMemberId==0;
	}
	public void logout() {
		loginedMemberId=0;		
	}
	public void setCurrentBoardCode(String boardCode) {
		this.getCurrentBoardCode=boardCode;
		
	}
	

}
