package com.sbs.example.ucong.container;

import java.util.Scanner;

import com.sbs.example.ucong.controller.ArticleController;

public class Container {

	public static Scanner scanner;
	public static ArticleController articleController;
	
	static {
		scanner = new Scanner(System.in);
		articleController = new ArticleController();
	}

}
