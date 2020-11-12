package com.sbs.example.ucong;

import java.util.Scanner;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.controller.ArticleController;
import com.sbs.example.ucong.controller.Controller;

public class App {
	private ArticleController articleController;
	
	public App() {
		articleController = Container.articleController;
	}

	public void run() {
		Scanner sc = Container.scanner;
		
		while(true) {
			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine();
			
			if(cmd.equals("system eixt")) {
				System.out.println("== 프로그램 종료 ==");
				break;
			}
			
			Controller controller = getControllerByCmd(cmd);
			if(controller != null) {
				controller.doCommand(cmd);
			}
			
			
		}
	}

	private Controller getControllerByCmd(String cmd) {
		if(cmd.startsWith("article")) {
			return articleController;
		}
		return null;
	}

}
