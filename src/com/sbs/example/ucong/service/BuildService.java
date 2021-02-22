package com.sbs.example.ucong.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Board;
import com.sbs.example.ucong.dto.Tag;
import com.sbs.example.ucong.util.Util;

public class BuildService {
	private ArticleService articleService;
	private MemberService memberService;
	private DisqusApiService disqusApiService;

	public BuildService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		disqusApiService = Container.disqusApiService;
	}

	public void buildSite() {
		System.out.println("site 폴더생성");
		Util.mkdirs("site");

		Util.copyDir("site_template/img", "site/img");

		Util.copy("site_template/part/favicon.ico", "site/favicon.ico");
		Util.copy("site_template/part/app.css", "site/app.css");
		Util.copy("site_template/part/app.js", "site/app.js");
		Util.copy("site_template/part/ads.txt", "site/ads.txt");

		loadDataFromDisqus();
		loadDataFromGa4Data();

		buildIndexPage();
		buildAboutPage();
		buildArticleTagPage();
		buildArticleSearchPage();
		buildArticleDetailPages();
		buildArticleListPages();
		buildStatisticsPage();

	}

	private void buildAboutPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("about");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String html = Util.getFileContents("site_template/part/about.html");

		sb.append(head);
		sb.append(html);
		sb.append(foot);

		String filePath = "site/about.html";
		Util.writeFile(filePath, sb.toString());
	}

	public void buildArticleTagPage() {
		Map<String, List<Article>> articlesByTagMap = articleService.getArticlesByTagMap();

		String jsonText = Util.getJsonText(articlesByTagMap);
		Util.writeFile("site/article_tag.json", jsonText);
	}

	private void buildArticleSearchPage() {
		List<Article> articles = articleService.getForPrintArticles(0);
		String jsonText = Util.getJsonText(articles);
		Util.writeFile("site/article_list.json", jsonText);

		Util.copy("site_template/part/article_search.js", "site/article_search.js");
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("article_search");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String html = Util.getFileContents("site_template/part/search.html");

		html = html.replace("${articlesCount}", articles.size() + "");

		sb.append(head);
		sb.append(html);
		sb.append(foot);

		String filePath = "site/article_search.html";
		Util.writeFile(filePath, sb.toString());
	}

	private void loadDataFromGa4Data() {
		Container.googleAnalyticsApiService.updatePageHits();
	}

	private void loadDataFromDisqus() {
		Container.disqusApiService.updateArticlesCounts();

	}

	// 인덱스 페이지
	private void buildIndexPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/part/foot.html");

		String bodyTemplate = Util.getFileContents("site_template/part/index.html");

		StringBuilder mainContent = new StringBuilder();

		List<Article> ratestArticles = articleService.getForPrintRatestArticles();

		for (Article ratestArticle : ratestArticles) {

			String link = getArticleDetailFileName(ratestArticle.getId());

			mainContent.append("<div>");
			mainContent.append("<div class=\"article-list__cell-title\"><a href=\"" + link
					+ "\"class=\"hover-underline\">" + ratestArticle.getTitle() + "</a></div>");
			mainContent.append("<div class=\"article-list__cell-body\">" + ratestArticle.getBody() + "</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append(
					"<div class=\"article-list__cell-writer\">" + ratestArticle.getExtra__memberName() + "</div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-board-name\"><a href=\"article_list_"
					+ ratestArticle.getExtra__boardCode() + "_1.html\">" + ratestArticle.getExtra__boardName()
					+ "</a></div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-reg-date\">" + ratestArticle.getRegDate() + "</div>");
			mainContent.append("</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append("<div class=\"article-list__cell-hit\"><i class=\"far fa-eye\"></i> "
					+ ratestArticle.getHitCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-like\"><i class=\"far fa-heart\"></i> "
					+ ratestArticle.getLikesCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-comment\"><i class=\"far fa-comment-dots\"></i> "
					+ ratestArticle.getCommentsCount() + "</div>");
			mainContent.append("</div>");

			mainContent.append("</div>");

		}

		String body = bodyTemplate.replace("${article-list__ratest-5}", mainContent.toString());

		mainContent.setLength(0);

		List<Article> bestArticles = articleService.getForPrintbestArticles();

		for (Article bestArticle : bestArticles) {

			String link = getArticleDetailFileName(bestArticle.getId());

			mainContent.append("<div>");
			mainContent.append("<div class=\"article-list__cell-title\"><a href=\"" + link
					+ "\"class=\"hover-underline\">" + bestArticle.getTitle() + "</a></div>");
			mainContent.append("<div class=\"article-list__cell-body\">" + bestArticle.getBody() + "</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append(
					"<div class=\"article-list__cell-writer\">" + bestArticle.getExtra__memberName() + "</div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-board-name\"><a href=\"article_list_"
					+ bestArticle.getExtra__boardCode() + "_1.html\">" + bestArticle.getExtra__boardName()
					+ "</a></div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-reg-date\">" + bestArticle.getRegDate() + "</div>");
			mainContent.append("</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append("<div class=\"article-list__cell-hit\"><i class=\"far fa-eye\"></i> "
					+ bestArticle.getHitCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-like\"><i class=\"far fa-heart\"></i> "
					+ bestArticle.getLikesCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-comment\"><i class=\"far fa-comment-dots\"></i> "
					+ bestArticle.getCommentsCount() + "</div>");
			mainContent.append("</div>");

			mainContent.append("</div>");

		}

		body = body.replace("${article-list__best-5}", mainContent.toString());

		sb.append(head);
		sb.append(body);
		sb.append(foot);
		String fileName = "index.html";
		String filePath = "site/" + fileName;
		Util.writeFile(filePath, sb.toString());
	}

	// 통계 페이지 생성
	private void buildStatisticsPage() {
		String head = getHeadHtml("article_statistics");
		String bodyTemplate = Util.getFileContents("site_template/part/stat.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		StringBuilder sb = new StringBuilder();

		sb.append(head);

		// 전체 게시물수
		String body = bodyTemplate.replace("${article-stat__totalArticlesCount}", articleService.getArticlesCount() + " 개");
		// 전체 게시물 조회수
		body = body.replace("${article-stat__totalArticlesHit}", articleService.getArticlesHitCount() + " 회");

		StringBuilder bdBodyArticles = new StringBuilder();
		StringBuilder bdBodyHit = new StringBuilder();
		List<Board> boards = articleService.getBoards();
		for (Board board : boards) {
			// 각 게시판별 게시물수
			bdBodyArticles.append(articleService.getArticlesCountByBoardId(board.getId()) +",");
			// 각 게시판별 게시물 조회수
			bdBodyHit.append(articleService.getBoardArticlesHitCountByBoardId(board.getId()) +",");
		}
		
		bdBodyArticles.deleteCharAt(bdBodyArticles.lastIndexOf(","));
		bdBodyHit.deleteCharAt(bdBodyHit.lastIndexOf(","));
		
		body = body.replace("${article-stat__boardArticlesCount}", bdBodyArticles.toString());
		body = body.replace("${article-stat__boardArticlesHit}", bdBodyHit.toString());
		sb.append(body);
		sb.append(foot);

		String filePath = "site/stat.html";
		Util.writeFileContents(filePath, sb.toString());
	}

	// 리스트
	private void buildArticleListPages() {
		List<Board> boards = articleService.getBoards();

		int itemsInAPage = 10;
		int pageBoxSize = 10;

		for (Board board : boards) {
			List<Article> articles = articleService.getForPrintArticles(board.getId());
			int articleCount = articles.size();
			int totalPage = (int) Math.ceil((double) articleCount / itemsInAPage);
			if (totalPage <= 0) {
				totalPage = 1;
			}
			for (int i = 1; i <= totalPage; i++) {
				buildArticleListPage(board, itemsInAPage, pageBoxSize, totalPage, articles, i);
			}
		}
	}

	private void buildArticleListPage(Board board, int itemsInAPage, int pageBoxSize, int totalPage,
			List<Article> articles, int page) {
		StringBuilder sb = new StringBuilder();

		// 헤더시작
		sb.append(getHeadHtml("article_list_" + board.getCode()));

		// 바디 시작
		String bodyTemplate = Util.getFileContents("site_template/part/list.html");

		StringBuilder mainContent = new StringBuilder();

		int articlesCount = articles.size();
		int start = (page - 1) * itemsInAPage;
		int end = start + itemsInAPage - 1;

		if (end >= articlesCount) {
			end = articlesCount - 1;
		}

		if (articlesCount == 0) {
			mainContent.append("<div class= \"flex flex-jc-c\">");
			mainContent.append("<div> 등록된 게시물이 없습니다. </div>");
			mainContent.append("</div>");
		}

		for (int i = start; i <= end; i++) {
			Article article = articles.get(i);

			String link = getArticleDetailFileName(article.getId());

			mainContent.append("<div>");
			mainContent.append("<div class=\"article-list__cell-title\"><a href=\"" + link
					+ "\"class=\"hover-underline\">" + article.getTitle() + "</a></div>");
			mainContent.append("<div class=\"article-list__cell-body\">" + article.getBody() + "</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append("<div class=\"article-list__cell-writer\">" + article.getExtra__memberName() + "</div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-board-name\"><a href=\"article_list_"
					+ article.getExtra__boardCode() + "_1.html\">" + article.getExtra__boardName() + "</a></div>");
			mainContent.append("<span>ㆍ</span>");
			mainContent.append("<div class=\"article-list__cell-reg-date\">" + article.getRegDate() + "</div>");
			mainContent.append("</div>");

			mainContent.append("<div class=\"flex\">");
			mainContent.append("<div class=\"article-list__cell-hit\"><i class=\"far fa-eye\"></i> "
					+ article.getHitCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-like\"><i class=\"far fa-heart\"></i> "
					+ article.getLikesCount() + "</div>");
			mainContent.append("<div class=\"article-list__cell-comment\"><i class=\"far fa-comment-dots\"></i> "
					+ article.getCommentsCount() + "</div>");
			mainContent.append("</div>");

			mainContent.append("</div>");

		}
		StringBuilder pageMenuContent = new StringBuilder();

		// 현재 페이지 계산
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}

		// 현재 페이지 박스 시작, 끝 시작
		int previousPageBoxesCount = (page - 1) / pageBoxSize;
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;

		if (pageBoxEndPage > totalPage) {
			pageBoxEndPage = totalPage;
		}

		// 이전버튼 페이지 계산
		int pageBoxStartBeforePage = pageBoxStartPage - 1;
		if (pageBoxStartBeforePage < 1) {
			pageBoxStartBeforePage = 1;
		}

		// 다음버튼 페이지 계산
		int pageBoxEndAfterPage = pageBoxEndPage + 1;
		if (pageBoxEndAfterPage > totalPage) {
			pageBoxEndAfterPage = totalPage;
		}

		// 이전버튼 노출여부 계산
		boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;

		// 다음버튼 노출여부 계산
		boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;

		if (pageBoxStartBeforeBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, pageBoxStartBeforePage)
					+ "\" class=\"flex flex-ai-c\">&lt;이전</a></li>");
		}

		for (int i = pageBoxStartPage; i <= pageBoxEndPage; i++) {
			String selectedClass = "";

			if (i == page) {
				selectedClass = "article-page-menu__link--selected";
			}
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, i) + "\" class=\"flex flex-ai-c "
					+ selectedClass + " \">" + i + "</a></li>");
		}

		if (pageBoxEndAfterBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, pageBoxEndAfterPage)
					+ "\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
		}

		String body = bodyTemplate.replace("${board-name}", board.getName());
		body = body.replace("${board-totalArticleCount}", articles.size() + "");
		body = body.replace("${article-list__content}", mainContent.toString());
		body = body.replace("${article-page-menu__content}", pageMenuContent.toString());

		sb.append(body);

		// 푸터 시작
		sb.append(Util.getFileContents("site_template/part/foot.html"));

		String fileName = getArticleListFileName(board, page);
		String filePath = "site/" + fileName;

		Util.writeFileContents(filePath, sb.toString());
	}

	private String getArticleListFileName(Board board, int page) {
		return getArticleListFileName(board.getCode(), page);
	}

	private String getArticleListFileName(String boardCode, int page) {
		return "article_list_" + boardCode + "_" + page + ".html";
	}

	// 게시물 상세 페이지
	private void buildArticleDetailPages() {
		List<Board> boards = articleService.getForPrintBoards();
		String bodyTemplate = Util.getFileContents("site_template/part/detail.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		for (Board board : boards) {
			List<Article> articles = articleService.getForPrintArticles(board.getId());
			for (int i = 0; i < articles.size(); i++) {
				Article article = articles.get(i);

				String head = getHeadHtml("article_detail", article);

				Article prevArticle = null;
				int prevArticleIndex = i + 1;
				int prevArticleId = 0;

				if (prevArticleIndex < articles.size()) {
					prevArticle = articles.get(prevArticleIndex);
					prevArticleId = prevArticle.getId();
				}

				Article nextArticle = null;
				int nextArticleIndex = i - 1;
				int nextArticleId = 0;

				if (nextArticleIndex >= 0) {
					nextArticle = articles.get(nextArticleIndex);
					nextArticleId = nextArticle.getId();
				}

				StringBuilder sb = new StringBuilder();

				// 헤더추가
				sb.append(head);

				String articleBodyForPrint = article.getBody();
				articleBodyForPrint = articleBodyForPrint.replaceAll("script", "t-script");

				String body = bodyTemplate.replace("${article-detail__title}", article.getTitle());
				body = body.replace("${article-detail__board-name}", article.getExtra__boardName());
				body = body.replace("${article-detail__writer}", article.getExtra__memberName());
				body = body.replace("${article-detail__reg-date}", article.getRegDate());
				body = body.replace("${article-detail__hit-count}", article.getHitCount() + "");
				body = body.replace("${article-detail__likes-count}", article.getLikesCount() + "");
				body = body.replace("${article-detail__comments-count}", article.getCommentsCount() + "");

				body = body.replace("${article-detail__body}", articleBodyForPrint);

				List<Tag> tags = Container.tagService.getDedupTagsByArticleId(article.getId());

				StringBuilder tagBodies = new StringBuilder();

				if (tags != null) {
					for (Tag tag : tags) {
						tagBodies.append("<div>" + tag.getBody() + "</div>");
					}
				}

				body = body.replace("${article-detail__tag}", tagBodies);

				body = body.replace("${article-detail__link-list-url}",
						getArticleListFileName(article.getExtra__boardCode(), 1));

				body = body.replace("${article-detail__link-prev-article-url}",
						getArticleDetailFileName(prevArticleId));
				body = body.replace("${article-detail__link-prev-article-title-attr}",
						prevArticle != null ? prevArticle.getTitle() : "");
				body = body.replace("${article-detail__link-prev-article-class-addi}",
						prevArticleId == 0 ? "none" : "");

				body = body.replace("${article-detail__link-next-article-url}",
						getArticleDetailFileName(nextArticleId));
				body = body.replace("${article-detail__link-next-article-title-attr}",
						nextArticle != null ? nextArticle.getTitle() : "");
				body = body.replace("${article-detail__link-next-article-class-addi}",
						nextArticleId == 0 ? "none" : "");

				body = body.replace("${site-domain}", "blog.heycong.com");
				body = body.replace("${file-name}", getArticleDetailFileName(article.getId()));

				sb.append(body);
				sb.append(foot);

				String fileName = getArticleDetailFileName(article.getId());
				String filePath = "site/" + fileName;
				Util.writeFileContents(filePath, sb.toString());
			}
		}

	}

	public String getArticleDetailFileName(int id) {
		return "article_detail_" + id + ".html";
	}

	// 헤더 게시판 메뉴 동적생성
	private String getHeadHtml(String pageName, Object relObj) {
		String head = Util.getFileContents("site_template/part/head.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();

		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			String link = getArticleListFileName(board, 1);
			boardMenuContentHtml.append("<li>");
			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");
			boardMenuContentHtml.append("<span>" + board.getName() + "</span>");
			boardMenuContentHtml.append("</a>");
			boardMenuContentHtml.append("</li>");
		}

		head = head.replace("${tab-bar__menu-box-1__board-menu-content}", boardMenuContentHtml.toString());

		String pageTitle = getPageTitle(pageName, relObj);
		head = head.replace("${page-title}", pageTitle);

		String siteName = "HeyCong";
		String siteSubject = "개발자의 기술/일상 블로그";
		String siteDescription = "개발자의 기술/일상 관련 글들을 공유합니다.";
		String siteKeywords = "HTML, CSS, JAVASCRIPT, JAVA, SPRING, MySQL, 리눅스, 리액트";
		String siteDomain = "blog.heycong.com";
		String siteMainUrl = "https://" + siteDomain;
		String currentDate = Util.getNowDateStr().replace(" ", "T");

		if (relObj instanceof Article) {
			Article article = (Article) relObj;
			siteSubject = article.getTitle();
			siteDescription = article.getBody();
			siteDescription = siteDescription.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
		}

		head = head.replace("${site-name}", siteName);
		head = head.replace("${site-subject}", siteSubject);
		head = head.replace("${site-description}", siteDescription);
		head = head.replace("${site-domain}", siteDomain);
		head = head.replace("${site-domain}", siteDomain);
		head = head.replace("${current-date}", currentDate);
		head = head.replace("${site-main-url}", siteMainUrl);
		head = head.replace("${site-keywords}", siteKeywords);

		return head;
	}

	private String getPageTitle(String pageName, Object relObj) {
		StringBuilder sb = new StringBuilder();
		String forPrintPageName = pageName;

		if (forPrintPageName.startsWith("index")) {
			forPrintPageName = "home";
		}

		forPrintPageName = forPrintPageName.toUpperCase();
		forPrintPageName = forPrintPageName.replaceAll("_", " ");

		sb.append("Heycong | ");
		sb.append(forPrintPageName);

		if (relObj instanceof Article) {
			Article article = (Article) relObj;

			sb.insert(0, article.getTitle() + " | ");
		}

		return sb.toString();

	}

	private String getHeadHtml(String pageName) {
		return getHeadHtml(pageName, null);
	}

	// 안씀
	private String getTitleBarContentByPageName(String pageName) {
		if (pageName.startsWith("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_search")) {
			return "<i class=\"fas fa-search\"></i> <span>ARTICLE SEARCH</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_study")) {
			return "<i class=\"fas fa-pencil-alt\"></i> <span>STUDY LIST</span>";
		} else if (pageName.startsWith("article_list_daily")) {
			return "<i class=\"fab fa-dailymotion\"></i> <span>DAILY LIST</span>";
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
