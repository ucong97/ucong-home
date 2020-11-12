package com.sbs.example.ucong.controller;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.service.ArticleService;

public class ArticleController extends Controller {
	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}

	public void doCommand(String cmd) {
		if (cmd.equals("article list")) {
			showList(cmd);
		} else if (cmd.startsWith("article delete")) {
			delete(cmd);
		} else if (cmd.startsWith("article modify")) {
			modify(cmd);
		}
	}

	private void modify(String cmd) {
		System.out.println("== 게시물 수정 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("수정할 게시물 번호를 선택해주세요.");
			return;
		}
		int modifyId = Integer.parseInt(cmdBits[2]);
		System.out.printf("수정할 제목 : ");
		String title = Container.scanner.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = Container.scanner.nextLine();

		boolean existsId=articleService.modify(modifyId,title,body);
		if (existsId == false) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", modifyId);
			return;
		}
		System.out.printf("%d번 게시물이 수정되었습니다.\n",modifyId);
	}

	private void delete(String cmd) {
		System.out.println("== 게시물 삭제 ==");
		String[] cmdBits = cmd.split(" ");
		if (cmdBits.length <= 2) {
			System.out.println("삭제할 게시물 번호를 선택해주세요.");
			return;
		}
		int delectId = Integer.parseInt(cmdBits[2]);

		boolean existsId = articleService.getArticleById(delectId);

		if (existsId == false) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", delectId);
			return;
		}

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", delectId);

	}

	private void showList(String cmd) {
		System.out.println("== 게시물 리스트 ==");
		System.out.println("번호 / 작성 / 수정 / 작성자 / 제목");

		List<Article> articles = articleService.getArticles();

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %d / %s\n", article.id, article.regDate, article.updateDate,
					article.memberId, article.title);
		}
	}
}
