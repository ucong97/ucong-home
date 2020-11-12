package com.sbs.example.ucong.container;

import java.util.Scanner;

import com.sbs.example.ucong.controller.ArticleController;
import com.sbs.example.ucong.dao.ArticleDao;
import com.sbs.example.ucong.service.ArticleService;

public class Container {

	public static Scanner scanner;
	public static ArticleController articleController;
	public static ArticleService articleService;
	public static ArticleDao articleDao;
	
	static {
		scanner = new Scanner(System.in);
		articleDao = new ArticleDao();
		articleService = new ArticleService();
		articleController = new ArticleController();
	}

}
