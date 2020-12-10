package com.sbs.example.ucong.service;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Board;
import com.sbs.example.ucong.util.Util;

public class BuildService {
	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildSite() {
		System.out.println("site 폴더생성");
		Util.rmdir("site");
		Util.mkdirs("site");

		Util.copy("site_template/part/app.css", "site/app.css");

		String head = getHeadHtml();
		String foot = Util.getFileContents("site_template/part/foot.html");

		List<Board> boards = articleService.getBoards();

		// 각 게시판 별 게시물 리스트 페이지 생성
		for (Board board : boards) {
			StringBuilder sb = new StringBuilder();
			String fileName = board.code + "_article_list_1.html";
			String filePath = "site/" + fileName;
			List<Article> articles = articleService.getArticlesByBoardId(board.id);
			for (Article article : articles) {
				sb.append("<div>번호 : " + article.id + "</div>");
				sb.append("<div>작성일 : " + article.regDate + "</div>");
				sb.append("<div><a href=" + article.id + ".html>제목 :" + article.title + "</a></div>");

			}

			Util.writeFileContents(filePath, head + sb.toString() + foot);
		}
		System.out.println("각 게시판 별 게시물 리스트 페이지 생성");
		// 게시물 별 파일생성
		List<Article> articles = articleService.getArticles();
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			String fileName = article.id + ".html";
			String filePath = "site/" + fileName;

			sb.append("<div>");
			sb.append("번호 : " + article.id + "<br>");
			sb.append("작성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");

			if (article.id > 1) {
				sb.append("<a href=\"" + (article.id - 1) + ".html\">이전글</a>");
			}
			if (article.id < articles.size()) {
				sb.append("<a href=\"" + (article.id + 1) + ".html\">다음글</a>");
			}
			sb.append("</div>");
			Util.writeFileContents(filePath, head + sb.toString() + foot);
		}
		System.out.println("게시물 별 파일생성");

		// 모든 게시물 리스트 페이지 생성
		String fileName = "article_list.html";
		String filePath = "site/" + fileName;
		StringBuilder sb = new StringBuilder();
		for (Article article : articles) {
			sb.append("<div>번호 : " + article.id + " 제목 :<a href=\"" + article.id + ".html\"> " + article.title
					+ "</a></div>");
		}
		Util.writeFileContents(filePath, head + sb.toString() + foot);
		System.out.println("리스트 페이지 생성");

		// 인덱스 생성
		sb.setLength(0);
		fileName = "index.html";
		filePath = "site/" + fileName;
		Util.writeFileContents(filePath, head + sb.toString() + foot);
		System.out.println("인덱스 생성");
	}
	// 헤더 게시판 메뉴 동적생성
	private String getHeadHtml() {
		String head = Util.getFileContents("site_template/part/head.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");
			boardMenuContentHtml
					.append("<a href=\"" + board.code + "_article_list_1.html\" class=\"block text-align-center\">");
			String iClass = "fas fa-clipboard-list";

			if (board.code.contains("notice")) {
				iClass = "fab fa-free-code-camp";
			} else if (board.code.contains("free")) {
				iClass = "fab fa-free-code-camp";
			}
			boardMenuContentHtml.append("<i class=\"" + iClass + "\"></i>");

			boardMenuContentHtml.append(" ");
			boardMenuContentHtml.append("<span>");
			boardMenuContentHtml.append(board.code);
			boardMenuContentHtml.append("</span>");
			boardMenuContentHtml.append("</a>");
			boardMenuContentHtml.append("</li>");
		}
		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml.toString());

		return head;
	}

}
