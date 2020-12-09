package com.sbs.example.ucong;

import java.util.Scanner;

import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.controller.ArticleController;
import com.sbs.example.ucong.controller.BuildController;
import com.sbs.example.ucong.controller.Controller;
import com.sbs.example.ucong.controller.MemberController;

public class App {
	private ArticleController articleController;
	private MemberController memberController;
	private BuildController buildController;

	public App() {
		articleController = Container.articleController;
		memberController = Container.memberController;
		buildController = Container.buildController;		
	}


	public void run() {
		Scanner sc = Container.scanner;
		
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "textBoard");
			MysqlUtil.setDevMode(false);

			boolean needToExit = false;
			if (cmd.equals("system eixt")) {
				System.out.println("== 프로그램 종료 ==");
				needToExit = true;
			}

			Controller controller = getControllerByCmd(cmd);
			if (controller != null) {
				controller.doCommand(cmd);
			}
			MysqlUtil.closeConnection();
			if (needToExit == true) {
				break;
			}

		}

	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article")) {
			return articleController;
		} else if (cmd.startsWith("member")) {
			return memberController;
		} else if (cmd.startsWith("build")) {
			return buildController;
		}
		return null;
	}

}
