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
		//회원수
		List<Member> members = memberService.getmembers();
		System.out.println("회원수 = "+members.size());
		//전체 게시물수
		List<Article> articles= articleService.getArticles();
		System.out.println("전체 게시물수 = "+articles.size());
		//각 게시판별 게시물수
		List<Board> boards = articleService.getBoards();
		for(Board board:boards) {
			List<Article> boardArticles = articleService.getArticlesByBoardId(board.id);
			System.out.println(board.code+"board의 게시물수 = " + boardArticles.size());
		}
		//전체 게시물 조회수
		int hitSum=0;
		for(Article article : articles) {
			hitSum += article.hit;
		}
		System.out.println("전체게시물조회수 = " + hitSum);
		//각 게시판별 게시물 조회수
		for(Board board:boards) {
			List<Article> boardArticles = articleService.getArticlesByBoardId(board.id);
			int boardHitSum=0;
			for(Article article : boardArticles) {
				boardHitSum += article.hit;
			}
			System.out.println(board.code+"게시판 조회수 = " + boardHitSum);
		}
		
	}

	// 각 게시판 별 게시물 리스트 페이지 생성
	//${article-list__content}
	//<div class="flex">
    //<div class="article-list__cell-id">1</div>
    //<div class="article-list__cell-reg-date">2020-12-24</div>
    //<div class="article-list__cell-writer">루돌프</div>
    //<div class="article-list__cell-title"><a href="#" class="hover-underline">크리스마스이브</a></div>
    //</div>
	private void buildBoardArticlePages() {
		List<Board> boards = articleService.getBoards();
		String foot = Util.getFileContents("site_template/part/foot.html");
		for (Board board : boards) {
			String list = Util.getFileContents("site_template/part/list.html");
			String head = getHeadHtml("article_list_"+board.code);
			StringBuilder sb = new StringBuilder();
			String fileName = board.code + "_article_list_1.html";
			String filePath = "site/" + fileName;
			List<Article> articles = articleService.getArticlesByBoardId(board.id);
			for (Article article : articles) {
				Member member = memberService.getMemberById(article.memberId);
				sb.append("<div class=\"flex\">\n");
				sb.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
				sb.append("<div class=\"article-list__cell-reg-date\">" + "2020-12-12" + "</div>");
				sb.append("<div class=\"article-list__cell-writer\">"+ member.name + "</div>");
				sb.append("<div class=\"article-list__cell-title\"><a href=\"" + article.id + ".html\" class=\"hover-underline\">" + article.title + "</a></div>");
				sb.append("</div>\n");
			}
			
			list = list.replace("${article-list__content}",sb.toString());
			Util.writeFileContents(filePath, head + list + foot);
		}
		//System.out.println("각 게시판 별 게시물 리스트 페이지 생성");
	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.getArticles();
		String head = getHeadHtml("article_detail");
		String foot = Util.getFileContents("site_template/part/foot.html");
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			String fileName = article.id + ".html";
			String filePath = "site/" + fileName;

			sb.append("<section class=\"con-min-width\">");
			sb.append("<div class=\"con\">");
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
			sb.append("</section>");
			Util.writeFileContents(filePath, head + sb.toString() + foot);
		}
		//System.out.println("게시물 별 파일생성");

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
		//System.out.println(filePath+ "생성");
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
			
			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_"+board.code));
			boardMenuContentHtml.append("</a>");
			boardMenuContentHtml.append("</li>");
		}
		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml.toString());
		String titleBarContentHtml = getTitleBarContentByPageName(pageName);
		head = head.replace("${title-bar__content}", titleBarContentHtml);
		return head;
	}

	private String getTitleBarContentByPageName(String pageName) {
		if(pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		}else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fab fa-free-code-camp\"></i> <span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>NOTICE LIST</span>";
		}
		return "";
	}

}
