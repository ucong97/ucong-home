package com.sbs.example.ucong.service;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Board;
import com.sbs.example.ucong.util.Util;

public class BuildService {
	private ArticleService articleService;
	
	public BuildService() {
		articleService= Container.articleService;
	}
	
	public void buildSite() {
		System.out.println("site/article 폴더생성");
		Util.mkdirs("site/article");
		
		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");
		
		// 각 게시판 별 게시물 리스트 페이지 생성
		List<Board> boards = articleService.getBoards();
		
		for(Board board: boards) {
			String html="";
			String fileName= board.code + "_article_list_1.html";
			String filePath= "site/article/" + fileName;
			List<Article> articles = articleService.getArticlesByBoardId(board.id);
			for(Article article:articles) {
				html+= "<div>번호 : " + article.id + "</div>";
				html+= "<div>작성일 : " + article.regDate + "</div>";
				html+= "<div><a href=\""+article.id +".html\">제목 : " + article.title + "</a></div>";
			}
			html = head + html + foot;

			Util.writeFileContents(filePath, html);
		}
		
		//게시물 별 파일생성생성
		List<Article> articles = articleService.getArticles();
		for(Article article : articles) {
			
			String html = "";
			String fileName=article.id+".html";
			String filePath= "site/article/" + fileName;
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			html += "<div>내용 : " + article.body + "</div>";

			if(article.id>1) {
				html+= "<div><a href=\""+(article.id-1)+".html\">이전글</a></div>";
			}
			html += "<div><a href=\""+(article.id+1)+".html\">다음글</a></div>";

			html = head + html+ foot;
			Util.writeFileContents(filePath,html);
		}
		//모든 게시물 리스트 페이지 생성
		String html = "";
		String fileName="article_list.html";
		String filePath= "site/article/" + fileName;
		for(Article article : articles) {
			
			html+="<div>번호 : " + article.id + " 제목 :<a href=\""+article.id+".html\"> "+article.title+"</a></div>";
		}
		
		html = head + html+ foot;
		Util.writeFileContents(filePath,html);
		
		//인덱스 생성
		html = "";
		fileName="index.html";
		filePath= "site/article/" + fileName;
		html = head + html+ foot;
		Util.writeFileContents(filePath,html);
	}

}
