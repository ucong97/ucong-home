package com.sbs.example.ucong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.Board;

public class ArticleDao {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));

		}
		return articles;
	}

	public Article getArticle(int inputedId) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?",inputedId);
		
		Map<String,Object> articleMap = MysqlUtil.selectRow(sql);
		
		if(articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}

	public int delete(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?",inputedId);
		
		return MysqlUtil.delete(sql);

	}

	public int modify(int id, String title, String body) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET title=?,",title);
		sql.append("`body`=?,",body);
		sql.append("updateDate=NOW()");
		sql.append("WHERE id=?",id);
		
		return MysqlUtil.update(sql);
	}

	public int add(int memberId, int boardId, String title, String body) {
		
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate=NOW(),");
		sql.append("updateDate=NOW(),");
		sql.append("title=?,",title);
		sql.append("`body`=?,",body);
		sql.append("memberId=?,",memberId);
		sql.append("boardId=?",boardId);
		
		return MysqlUtil.insert(sql);
		
	}

	public int makeBoard(String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO board");
		sql.append("SET name = ?",name);
		
		return MysqlUtil.insert(sql);
	}

	public Board getBoardById(int inputedId) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?",inputedId);
		Map<String,Object> boardMap = MysqlUtil.selectRow(sql);
		
		if(boardMap.isEmpty()) {
			return null;
		}
	
		return new Board(boardMap);
	}

}
