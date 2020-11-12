package com.sbs.example.ucong.controller;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.service.ArticleService;

public class Controller {
	private ArticleService articleService;
	
	public Controller() {
		articleService = Container.articleService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {
			showList();
		}
	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		
		List<Article> articles = articleService.getArticles();
		
	}
}
