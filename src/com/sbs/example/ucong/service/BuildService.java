package com.sbs.example.ucong.service;

import java.io.File;
import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Member;
import com.sbs.example.ucong.util.Util;

public class BuildService {
	private ArticleService articleService;
	private MemberService memberService;
	
	public BuildService() {
		articleService= Container.articleService;
		memberService= Container.memberService;
	}
	public void makeHtml() {
		File htmlForder = new File("site");

		if ( htmlForder.exists() == false ) {
			htmlForder.mkdir();
		}
		List<Article> articles = articleService.getArticles();
		
		for(Article article:articles) {
			Member member = memberService.getMemberById(article.memberId);
			
			String fileName=article.id+".html";
			String html = "<meta charset=\"UTF-8\">";
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>작성자 : " + member.name + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			html += "<div>내용 : " + article.body + "</div>";
			
			if(article.id>1) {
				html+= "<div><a href=\""+(article.id-1)+".html\">이전글</a></div>";
			}
			html += "<div><a href=\""+(article.id+1)+".html\">다음글</a></div>";
			
			Util.writeFileContents("site/" + fileName, html);
		}
		
	}

}
