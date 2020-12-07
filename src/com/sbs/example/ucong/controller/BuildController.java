package com.sbs.example.ucong.controller;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.service.BuildService;

public class BuildController extends Controller{
	private BuildService buildService;
	
	public BuildController() {
		buildService = Container.buildService;
	}

	public void doCommand(String cmd) {
		if(cmd.equals("build html")) {
			doHtml(cmd);
		}
	}

	private void doHtml(String cmd) {
		System.out.println("== html 생성을 시작합니다. ==");
		buildService.makeHtml();
	}

}
