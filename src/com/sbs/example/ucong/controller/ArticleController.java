package com.sbs.example.ucong.controller;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.service.ArticleService;

public class ArticleController extends Controller{
private ArticleService articleService;
	
	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {
			showList();
		}
	}

	private void showList() {
		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목");
		
		List<Article> articles = articleService.getArticles();
		
		for(Article article: articles) {
			System.out.printf("%d / %s / %s / %d / %s\n",article.id,article.regDate,article.updateDate,article.memberId,article.title);
		}
	}
}
