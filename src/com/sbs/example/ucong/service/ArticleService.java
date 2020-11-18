package com.sbs.example.ucong.service;

import java.util.List;

import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.dao.ArticleDao;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.ArticleReply;
import com.sbs.example.ucong.dto.Board;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public int delete(int id) {
		return articleDao.delete(id);
	}

	public int modify(int id, String title, String body) {
		return articleDao.modify(id, title, body);
	}

	public int write(int memberId, int boardId, String title, String body) {
		return articleDao.add(memberId,boardId,title, body);
	}

	public int makeBoard(String name) {
		return articleDao.makeBoard(name);
	}

	public Board getBoardById(int inputedId) {
		return articleDao.getBoardById(inputedId);
	}

	public int WrtieReply(int articleId, int memberId,String body) {
		return articleDao.addReply(articleId,memberId, body);
	}

	public List<ArticleReply> getArticleReplysByArticleId(int articleId) {
		return articleDao.getArticleReplysByArticleId(articleId);
	}

}
