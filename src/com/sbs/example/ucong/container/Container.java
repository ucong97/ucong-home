package com.sbs.example.ucong.container;

import java.util.Scanner;

import com.sbs.example.ucong.AppConfig;
import com.sbs.example.ucong.controller.ArticleController;
import com.sbs.example.ucong.controller.BuildController;
import com.sbs.example.ucong.controller.MemberController;
import com.sbs.example.ucong.dao.ArticleDao;
import com.sbs.example.ucong.dao.MemberDao;
import com.sbs.example.ucong.service.ArticleService;
import com.sbs.example.ucong.service.BuildService;
import com.sbs.example.ucong.service.DisqusApiService;
import com.sbs.example.ucong.service.MemberService;
import com.sbs.example.ucong.session.Session;

public class Container {

	public static Scanner scanner;
	public static ArticleController articleController;
	public static ArticleService articleService;
	public static ArticleDao articleDao;
	public static MemberController memberController;
	public static MemberService memberService;
	public static MemberDao memberDao;
	public static Session session;
	public static BuildController buildController;
	public static BuildService buildService;
	public static DisqusApiService disqusApiService;
	public static AppConfig config;
	
	static {
		
		config = new AppConfig();
		
		scanner = new Scanner(System.in);
		session = new Session();
		
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		
		disqusApiService = new DisqusApiService();
		articleService = new ArticleService();
		memberService = new MemberService();
		buildService = new BuildService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
		buildController = new BuildController();
	}

}
