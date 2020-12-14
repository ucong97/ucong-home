package com.sbs.example.ucong.service;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Board;
import com.sbs.example.ucong.dto.Member;
import com.sbs.example.ucong.util.Util;

public class BuildService {
	private ArticleService articleService;
	private MemberService memberService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void buildSite() {
		System.out.println("site 폴더생성");
		Util.rmdir("site");
		Util.mkdirs("site");

		Util.copy("site_template/part/app.css", "site/app.css");

		buildIndexPage();
		buildArticleDetailPages();
		buildBoardArticlePages();
		buildStatisticsPage();

	}

	// 통계 페이지 생성
	private void buildStatisticsPage() {
		String head = getHeadHtml("article_statistics");
		String foot = Util.getFileContents("site_template/part/foot.html");
		StringBuilder sb = new StringBuilder();
		// 회원수
		int membersCount = memberService.getMembersCount();
		// 전체 게시물수
		int articlesCount = articleService.getArticlesCount();
		// 전체 게시물 조회수
		int articlesHitCount = articleService.getArticlesHitCount();

		
		List<Board> boards = articleService.getBoards();
		for (Board board : boards) {
			// 각 게시판별 게시물수
			int boardArticlesCount = articleService.getArticlesCountByBoardId(board.id);
			// 각 게시판별 게시물 조회수
			int boardArticlesHitCount = articleService.getBoardArticlesHitCountByBoardId(board.id);
			
		}
	
		
		String filePath = "site/stat.html";
		Util.writeFileContents(filePath, head +sb.toString() +foot);
	}

	private void buildBoardArticlePages() {
		List<Board> boards = articleService.getBoards();
		String foot = Util.getFileContents("site_template/part/foot.html");
		for (Board board : boards) {
			String list = Util.getFileContents("site_template/part/list.html");
			String head = getHeadHtml("article_list_" + board.code);
			StringBuilder sb = new StringBuilder();
			String fileName = board.code + "_article_list_1.html";
			String filePath = "site/" + fileName;
			List<Article> articles = articleService.getArticlesByBoardId(board.id);
			for (Article article : articles) {
				Member member = memberService.getMemberById(article.memberId);
				int recommandCount = articleService.getRecommandCount(article.id);
				sb.append("<div class=\"flex\">\n");
				sb.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
				sb.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
				sb.append("<div class=\"article-list__cell-writer\">" + member.name + "</div>");
				sb.append("<div class=\"article-list__cell-title\"><a href=\"" + article.id
						+ ".html\" class=\"hover-underline\">" + article.title + "</a></div>");
				sb.append("<div class=\"article-list__cell-hit\">" + article.hit + "</div>");
				sb.append("<div class=\"article-list__cell-recommand\">" + recommandCount + "</div>");
				sb.append("</div>\n");
			}

			list = list.replace("${article-list__content}", sb.toString());
			Util.writeFileContents(filePath, head + list + foot);
		}
		// System.out.println("각 게시판 별 게시물 리스트 페이지 생성");
	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.getArticles();
		String head = getHeadHtml("article_detail");
		String foot = Util.getFileContents("site_template/part/foot.html");

		for (Article article : articles) {
			String detail = Util.getFileContents("site_template/part/detail.html");
			StringBuilder sb = new StringBuilder();

			Member member = memberService.getMemberById(article.memberId);
			int recommandCount = articleService.getRecommandCount(article.id);

			String fileName = article.id + ".html";
			String filePath = "site/" + fileName;

			sb.append("<h1>" + article.title + "</h1>");
			sb.append("<div class=\"article-detail__info flex\">");
			sb.append("<div class=\"article-detail__cell-writer\">" + member.name + "</div>");
			sb.append("<div class=\"article-detail__cell-reg-date\">" + article.regDate + "</div>");
			sb.append("<div class=\"count flex\">");
			sb.append("<div class=\"article-detail__cell-hit\">조회수</div>" + article.hit);
			sb.append("<div class=\"article-detail__cell-recommand\">추천수</div>" + recommandCount);
			sb.append("</div>");
			sb.append("</div>");

			detail = detail.replace("${article-detail-top}", sb.toString());

			sb.setLength(0);
			sb.append(article.body);
			detail = detail.replace("${article-detail-content}", sb.toString());

			sb.setLength(0);

			String boardCode = articleService.getBoardCodeById(article.boardId);
			sb.append("<a href=\"" + boardCode + "_article_list_1.html\" class=\"hover-underline\">목록</a>");

			if (article.id > 1) {
				sb.append("<a href=\"" + (article.id - 1) + ".html\" class=\"hover-underline\">&lt;이전글</a>");
			}
			if (article.id < articles.size()) {
				sb.append("<a href=\"" + (article.id + 1) + ".html\" class=\"hover-underline\">다음글&gt;</a>");
			}

			detail = detail.replace("${article-detail-bottom}", sb.toString());
			Util.writeFileContents(filePath, head + detail + foot);

		}

		// System.out.println("게시물 별 파일생성");

	}

	private void buildIndexPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String mainHtml = Util.getFileContents("site_template/part/index.html");

		sb.append(head);
		sb.append(mainHtml);
		sb.append(foot);

		String filePath = "site/index.html";
		Util.writeFile(filePath, sb.toString());
		// System.out.println(filePath+ "생성");
	}

	// 헤더 게시판 메뉴 동적생성
	private String getHeadHtml(String pageName) {
		String head = Util.getFileContents("site_template/part/head.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			boardMenuContentHtml.append("<li>");
			boardMenuContentHtml
					.append("<a href=\"" + board.code + "_article_list_1.html\" class=\"block text-align-center\">");

			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code));
			boardMenuContentHtml.append("</a>");
			boardMenuContentHtml.append("</li>");
		}
		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml.toString());
		String titleBarContentHtml = getTitleBarContentByPageName(pageName);
		head = head.replace("${title-bar__content}", titleBarContentHtml);
		return head;
	}

	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fab fa-free-code-camp\"></i> <span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>" + pageName.split("_")[2].toUpperCase()
					+ " LIST</span>";
		} else if (pageName.equals("article_statistics")) {
			return "<i class=\"fas fa-chart-pie\"></i> <span>STATISTICS</span>";
		}
		return "";
	}

}
