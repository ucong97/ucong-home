package com.sbs.example.ucong.service;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dao.MemberDao;
import com.sbs.example.ucong.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService() {
		memberDao= Container.memberDao;
	}

	public int join(String loginId, String loginPw, String name) {
		return memberDao.join(loginId,loginPw,name);
		
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int memberId) {
		// TODO Auto-generated method stub
		return memberDao.getMemberById(memberId);
	}

	

}
