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
		buildArticleListPages();
//		buildStatisticsPage();

	}

	// 통계 페이지 생성
//	private void buildStatisticsPage() {
//		String head = getHeadHtml("article_statistics");
//		String foot = Util.getFileContents("site_template/part/foot.html");
//		StringBuilder sb = new StringBuilder();
//		// 회원수
//		int membersCount = memberService.getMembersCount();
//		// 전체 게시물수
//		int articlesCount = articleService.getArticlesCount();
//		// 전체 게시물 조회수
//		int articlesHitCount = articleService.getArticlesHitCount();
//
//		
//		List<Board> boards = articleService.getBoards();
//		for (Board board : boards) {
//			// 각 게시판별 게시물수
//			int boardArticlesCount = articleService.getArticlesCountByBoardId(board.id);
//			// 각 게시판별 게시물 조회수
//			int boardArticlesHitCount = articleService.getBoardArticlesHitCountByBoardId(board.id);
//			
//		}
//	
//		
//		String filePath = "site/stat.html";
//		Util.writeFileContents(filePath, head +sb.toString() +foot);
//	}

	//리스트
	private void buildArticleListPages() {
		List<Board> boards = articleService.getBoards();
		
		int itemsInAPage= 10;
		int pageBoxSize= 10;
		
		for(Board board:boards) {
			List<Article> articles = articleService.getForPrintArticles(board.id);
			int articleCount = articles.size();
			int totalPage = (int)Math.ceil((double)articleCount/itemsInAPage);
			
			for(int i= 1 ; i <=totalPage;i++) {
				buildArticleListPage(board, itemsInAPage, pageBoxSize, articles, i);
			}
		}
	}
	private void buildArticleListPage(Board board, int itemsInAPage, int pageBoxSize, List<Article> articles, int page) {
			StringBuilder sb = new StringBuilder();
			
			// 헤더시작
			sb.append(getHeadHtml("article_list_" + board.code));
			
			// 바디 시작
			String bodyTemplate = Util.getFileContents("site_template/part/list.html");
			
			StringBuilder mainContent = new StringBuilder();
			
			int articlesCount= articles.size();
			int start = (page - 1) * itemsInAPage;
			int end = start + itemsInAPage -1 ;
			
			if (end >= articlesCount) {
				end= articlesCount -1;
			}
			
			for(int i= start; i<= end; i++) {
				Article article = articles.get(i);
				
				String link = article.id + ".html";
				
				int recommandCount = articleService.getRecommandCount(article.id);
				mainContent.append("<div class=\"flex\">");
				mainContent.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
				mainContent.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
				mainContent.append("<div class=\"article-list__cell-writer\">" + article.extra__memberName + "</div>");
				mainContent.append("<div class=\"article-list__cell-title\"><a href=\""+link + "\"class=\"hover-underline\">" + article.title + "</a></div>");
				mainContent.append("<div class=\"article-list__cell-hit\">" + article.hit + "</div>");
				mainContent.append("<div class=\"article-list__cell-recommand\">" + recommandCount + "</div>");
				mainContent.append("</div>");
			}
			
			
			StringBuilder pageMenuContent = new StringBuilder();
			
			// 토탈 페이지 계산
			int totalPage = (int)Math.ceil((double)articlesCount/itemsInAPage);
			
			// 현재 페이지 계산
			if(page<1) {
				page = 1;
			}
			if(page>totalPage) {
				page = totalPage;
			}
			
			// 현재 페이지 박스 시작, 끝 시작
			int previousPageBoxesCount= (page-1) / pageBoxSize;
			int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
			int pageBoxEndPage= pageBoxStartPage + pageBoxSize-1;
			
			if(pageBoxEndPage > totalPage ) {
				pageBoxEndPage=totalPage;
			}
			
			// 이전버튼 페이지 계산
			int pageBoxStartBeforePage = pageBoxStartPage - 1 ;
			if(pageBoxStartBeforePage<1) {
				pageBoxStartBeforePage=1;
			}

			
			// 다음버튼 페이지 계산
			int pageBoxEndAfterPage = pageBoxEndPage + 1 ;
			if(pageBoxEndAfterPage > totalPage) {
				pageBoxEndAfterPage=totalPage;
			}
			
			// 이전버튼 노출여부 계산
			boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
			
			// 다음버튼 노출여부 계산
			boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;
			
			if(pageBoxStartBeforeBtnNeedToShow) {
				pageMenuContent.append("<li><a href=\""+ getArticleListFileName(board,pageBoxStartBeforePage) +"\" class=\"flex flex-ai-c\">&lt;이전</a></li>");
			}
			
			for (int i= pageBoxStartPage ; i<=pageBoxEndPage; i++) {
				String selectedClass="";
				
				if(i==page) {
					selectedClass =  "article-page-menu__link--selected";
				}
				pageMenuContent.append("<li><a href=\""+ getArticleListFileName(board,i)+ "\" class=\"flex flex-ai-c "+ selectedClass +" \">"+i+"</a></li>");
			}
			
			if(pageBoxEndAfterBtnNeedToShow) {
				pageMenuContent.append("<li><a href=\""+ getArticleListFileName(board,pageBoxEndAfterPage) +"\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
			}
			
			String body= bodyTemplate.replace("${article-list__content}", mainContent.toString());
			body = body.replace("${article-page-menu__content}", pageMenuContent.toString());
			
			sb.append(body);
			
			// 푸터 시작
			sb.append(Util.getFileContents("site_template/part/foot.html"));
			
			String fileName = getArticleListFileName(board,page);
			String filePath = "site/" + fileName;
			
			Util.writeFileContents(filePath, sb.toString());
			System.out.println(filePath + "생성");
		}

	private String getArticleListFileName(Board board, int page) {
		return "article_list_"+ board.code + "_" + page + ".html";
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
			String link = getArticleListFileName(board,1);
			boardMenuContentHtml.append("<li>");
			boardMenuContentHtml
					.append("<a href=\"" + link +"\" class=\"block text-align-center\">");

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
