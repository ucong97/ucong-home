package com.sbs.example.ucong.controller;

import java.util.Scanner;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Member;
import com.sbs.example.ucong.service.MemberService;

public class MemberController extends Controller {
	private Scanner sc;
	private MemberService memberService;

	public MemberController() {
		sc = Container.scanner;
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			doJoin(cmd);
		}else if (cmd.equals("member login")) {
			doLogin(cmd);
		}else if (cmd.equals("member logout")) {
		doLogout(cmd);
	}
	}

	private void doLogout(String cmd) {
		if(Container.session.logouted()) {
			System.out.println("로그인 되어있지않습니다.");
			return;
		}
		System.out.println("== 로그아웃 ==");
		Container.session.logout();
	}

	private void doLogin(String cmd) {
		if(Container.session.logined()) {
			System.out.println("이미 로그인 되어있습니다.");
			return;
		}
		System.out.println("== 로그인 ==");
		String loginId = "";
		String loginPw = "";
		System.out.printf("아이디 입력: ");
		loginId = sc.nextLine().trim();
		Member member = memberService.getMemberByLoginId(loginId);
		if(member==null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		}
		System.out.printf("비밀번호 입력: ");
		loginPw = sc.nextLine().trim();
		if(member.loginPw.equals(loginPw)==false) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
		Container.session.login(member.id);
		
		System.out.printf("%s 회원님, 로그인 성공!\n",member.name);
	}

	private void doJoin(String cmd) {
		System.out.println("== 회원가입 ==");
		String loginId = "";
		String loginPw = "";
		String name = "";
		System.out.printf("아이디 입력: ");
		loginId = sc.nextLine().trim();
		
		Member member = memberService.getMemberByLoginId(loginId);
		if(member!=null) {
			System.out.println("이미  존재하는 아이디 입니다.");
			return;
		}
		System.out.printf("비밀번호 입력: ");
		loginPw = sc.nextLine().trim();
		System.out.printf("이름 입력: ");
		name = sc.nextLine().trim();
		
		int id =memberService.join(loginId,loginPw,name);
		
		System.out.printf("%d 회원님, 회원가입성공!\n",id);
	}

}
