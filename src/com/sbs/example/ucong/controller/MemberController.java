package com.sbs.example.ucong.controller;

import java.util.Scanner;

import com.sbs.example.ucong.container.Container;
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
		}
	}

	private void doJoin(String cmd) {
		System.out.println("== 회원가입 ==");
		String loginId = "";
		String loginPw = "";
		String name = "";
		System.out.printf("아이디 입력: ");
		loginId = sc.nextLine().trim();
		System.out.printf("비밀번호 입력: ");
		loginPw = sc.nextLine().trim();
		System.out.printf("이름 입력: ");
		name = sc.nextLine().trim();
		
		memberService.join(loginId,loginPw,name);
		
		System.out.printf("%s 회원님, 회원가입성공!\n",name);
	}

}
