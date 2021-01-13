package com.sbs.example.ucong.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.ucong.apidto.DisqusApiDataListThread;
import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.util.Util;

public class DisqusApiService {

	public Map<String, Object> getArticleDate(Article article) {
		String fileName = Container.buildService.getArticleDetailFileName(article.getId());

		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread disqusApiDataListThread = (DisqusApiDataListThread) Util.callApiResponseTo(
				DisqusApiDataListThread.class, url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=" + Container.config.getDisqusForumName(), "thread:ident=" + fileName);

		if (disqusApiDataListThread == null) {
			return null;
		}

		Map<String, Object> rs = new HashMap<>();
		rs.put("likesCount", disqusApiDataListThread.response.get(0).likes);
		rs.put("commentsCount", disqusApiDataListThread.response.get(0).posts);

		return rs;
	}

	public void updateArticlesCounts() {
List<Article> articles = Container.articleService.getForPrintArticles();
		
		for(Article article:articles) {
			Map<String, Object> disqusArticleData = Container.disqusApiService.getArticleDate(article);
			
			if(disqusArticleData != null) {
				int likesCount = (int)disqusArticleData.get("likesCount");
				int commentCount = (int)disqusArticleData.get("commentsCount");		
				
				Map<String, Object> modifyArgs = new HashMap<>();
				modifyArgs.put("id", article.getId());
				modifyArgs.put("likesCount", likesCount);
				modifyArgs.put("commentsCount", commentCount);
				
				Container.articleService.modify(modifyArgs);
			}
		}
	}

}
