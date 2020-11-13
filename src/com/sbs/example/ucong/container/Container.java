package com.sbs.example.ucong.container;

import java.util.Scanner;

import com.sbs.example.ucong.controller.ArticleController;
import com.sbs.example.ucong.controller.MemberController;
import com.sbs.example.ucong.dao.ArticleDao;
import com.sbs.example.ucong.dao.MemberDao;
import com.sbs.example.ucong.service.ArticleService;
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
	
	static {
		scanner = new Scanner(System.in);
		session = new Session();
		articleDao = new ArticleDao();
		memberDao = new MemberDao();
		articleService = new ArticleService();
		memberService = new MemberService();
		articleController = new ArticleController();
		memberController = new MemberController();
	}

}
