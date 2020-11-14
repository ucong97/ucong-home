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
		} else if (cmd.equals("member login")) {
			doLogin(cmd);
		} else if (cmd.equals("member logout")) {
			doLogout(cmd);
		} else if (cmd.equals("member whoami")){
			showWhoami(cmd);
		}
	}

	private void showWhoami(String cmd) {
		int memberId= Container.session.loginedMemberId;
		Member member = memberService.getMemberById(memberId);
		if(member==null) {
			System.out.println("로그인 되어있는 회원이 없습니다.");
			return;
		}
		System.out.printf("== %s 회원님 정보 ==\n",member.name);
		System.out.printf("번호 : %d\n",member.id);
		System.out.printf("아이디 : %s\n",member.loginId);
		System.out.printf("이름 : %s\n",member.name);
	}

	private void doLogout(String cmd) {
		if (Container.session.logouted()) {
			System.out.println("로그인 되어있지않습니다.");
			return;
		}
		System.out.println("== 로그아웃 ==");
		Container.session.logout();
	}

	private void doLogin(String cmd) {
		if (Container.session.logined()) {
			System.out.println("이미 로그인 되어있습니다.");
			return;
		}
		System.out.println("== 로그인 ==");
		String loginId = "";
		String loginPw = "";
		Member member = null;
		int loginIdFalseCount = 0;
		int loginIdFalseMaxCount = 3;
		boolean loginIdIsValid = false;
		while (true) {
			if (loginIdFalseCount >= loginIdFalseMaxCount) {
				System.out.println("아이디 입력을 3번 실패하셨습니다.");
				break;
			}
			System.out.printf("아이디 입력: ");
			loginId = sc.nextLine().trim();
			member = memberService.getMemberByLoginId(loginId);
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				loginIdFalseCount++;
				continue;
			}
			if (member == null) {
				System.out.println("존재하지 않는 아이디입니다.");
				loginIdFalseCount++;
				continue;
			}

			loginIdIsValid = true;
			break;
		}
		if (loginIdIsValid == false) {
			return;
		}

		int loginPwFalseCount = 0;
		int loginPwFalseMaxCount = 3;
		boolean loginPwIsValid = false;
		while (true) {
			if (loginPwFalseCount >= loginPwFalseMaxCount) {
				System.out.println("비밀번호 입력을 3번 실패하셨습니다.");
				break;
			}
			System.out.printf("비밀번호 입력: ");
			loginPw = sc.nextLine().trim();
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				loginPwFalseCount++;
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				loginPwFalseCount++;
				continue;
			}
			loginPwIsValid = true;
			break;
		}
		if (loginPwIsValid == false) {
			return;
		}

		Container.session.login(member.id);

		System.out.printf("%s 회원님, 로그인 성공!\n", member.name);
	}

	private void doJoin(String cmd) {
		System.out.println("== 회원가입 ==");
		String loginId = "";
		String loginPw = "";
		String name = "";
		int loginIdFalseCount = 0;
		int loginIdFalseMaxCount = 3;
		boolean loginIdIsValid = false;

		while (true) {
			if (loginIdFalseCount >= loginIdFalseMaxCount) {
				System.out.println("아이디 입력을 3번 실패하셨습니다.");
				break;
			}
			System.out.printf("아이디 입력: ");
			loginId = sc.nextLine().trim();

			Member member = memberService.getMemberByLoginId(loginId);
			if (member != null) {
				System.out.println("이미  존재하는 아이디 입니다.");
				loginIdFalseCount++;
				continue;
			}
			if (loginId.length() == 0) {
				System.out.println("회원가입할 아이디를 입력해주세요.");
				loginIdFalseCount++;
				continue;
			}

			loginIdIsValid = true;
			break;
		}

		if (loginIdIsValid == false) {
			return;
		}
		int loginPwFalseCount = 0;
		int loginPwFalseMaxCount = 3;
		boolean loginPwIsValid = false;
		while (true) {
			if (loginPwFalseCount >= loginPwFalseMaxCount) {
				System.out.println("비밀번호 입력을 3번 실패하셨습니다.");
				break;
			}
			System.out.printf("비밀번호 입력: ");
			loginPw = sc.nextLine().trim();
			if (loginPw.length() == 0) {
				System.out.println("회원가입할 비밀번호를 입력해주세요.");
				loginPwFalseCount++;
				continue;
			}

			loginPwIsValid = true;
			break;
		}
		if (loginPwIsValid == false) {
			return;
		}
		int loginNameFalseCount = 0;
		int loginNameFalseMaxCount = 3;
		boolean loginNameIsValid = false;
		while (true) {
			if (loginNameFalseCount >= loginNameFalseMaxCount) {
				System.out.println("이름 입력을 3번 실패하셨습니다.");
				break;
			}
			System.out.printf("이름 입력: ");
			name = sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요.");
				loginNameFalseCount++;
				continue;
			}

			loginNameIsValid = true;
			break;
		}
		if (loginNameIsValid == false) {
			return;
		}
		int id = memberService.join(loginId, loginPw, name);

		System.out.printf("%d 회원님, 회원가입성공!\n", id);
	}

}
