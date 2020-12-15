package com.sbs.example.ucong.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;
import com.sbs.example.ucong.dto.Article;
import com.sbs.example.ucong.dto.ArticleReply;
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
		sql.append("body=?,",body);
		sql.append("memberId=?,",memberId);
		sql.append("boardId=?,",boardId);
		sql.append("hit=0");
		
		return MysqlUtil.insert(sql);
		
	}

	public int makeBoard(String code, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO board");
		sql.append("SET regDate=NOW(),");
		sql.append("updateDate=NOW(),");
		sql.append("code = ?,",code);
		sql.append("name = ?",name);
		
		return MysqlUtil.insert(sql);
	}

	

	public int addReply(int articleId,int memberId, String body) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO articleReply");
		sql.append("SET regDate=NOW(),");
		sql.append("body=?,",body);
		sql.append("memberId=?,",memberId);
		sql.append("articleId=?",articleId);
		
		return MysqlUtil.insert(sql);
	}

	public List<ArticleReply> getArticleReplysByArticleId(int articleId) {
		List<ArticleReply> articleReplys = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE articleId = ? ",articleId);

		List<Map<String, Object>> articleReplyMapList = MysqlUtil.selectRows(sql);
		for (Map<String, Object> articleReplyMap : articleReplyMapList) {
			articleReplys.add(new ArticleReply(articleReplyMap));

		}
		return articleReplys;
	}

	public ArticleReply getArticleReply(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE id = ?",id);
		
		Map<String,Object> articleReplyMap = MysqlUtil.selectRow(sql);
		
		if(articleReplyMap.isEmpty()) {
			return null;
		}
		
		return new ArticleReply(articleReplyMap);
	}

	public int replyModify(int id, String body) {
		SecSql sql = new SecSql();
		sql.append("UPDATE articleReply");
		sql.append("SET `body`=?,",body);
		sql.append("regDate=NOW()");
		sql.append("WHERE id=?",id);
		
		return MysqlUtil.update(sql);
	}

	public int replyDelete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM articleReply");
		sql.append("WHERE id = ?",id);
		
		return MysqlUtil.delete(sql);
	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT a.*, m.name AS extra__memberName");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId=m.id");
		if(boardId!=0) {
			sql.append("WHERE A.boardId = ?",boardId);
		}
		sql.append("ORDER BY a.id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));

		}
		return articles;
	}

	public int hitCount(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET hit = hit+1");
		sql.append("WHERE id = ?",inputedId);
		
		return MysqlUtil.update(sql);
	
	}

	public int recommand(int articleId, int memberId) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO recommand(articleId,memberId,regDate,updateDate)");
		sql.append("SELECT ?,?,NOW(),NOW() FROM DUAL",articleId , memberId);
		sql.append("WHERE NOT EXISTS(SELECT * FROM recommand WHERE memberId=? AND articleId=?);",memberId,articleId);
		
		return MysqlUtil.update(sql);
	}

	public int cancleRecommand(int articleId, int memberId) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM recommand");
		sql.append("WHERE articleId=? and memberId=?",articleId,memberId);
		
		return MysqlUtil.delete(sql);
	}

	public int getRecommandCount(int inputedId) {
		SecSql sql = new SecSql();
		sql.append("SELECT count(*)");
		sql.append("FROM recommand");
		sql.append("WHERE articleId=?",inputedId);
		
		
		return MysqlUtil.selectRowIntValue(sql);
		
	}

	public Board getBoardByBoardCode(String boardCode) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE code = ?",boardCode);
		Map<String,Object> boardMap = MysqlUtil.selectRow(sql);
		
		if(boardMap.isEmpty()) {
			return null;
		}
	
		return new Board(boardMap);
	}

	public Board getBoardByName(String name) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE `name` = ?", name);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return null;
		}

		return new Board(map);
	}

	public List<Board> getForPrintBoards() {
		List<Board> boards = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT B.*");
		sql.append("FROM board AS B");
		sql.append("ORDER BY B.id DESC");

		List<Map<String, Object>> mapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : mapList) {
			boards.add(new Board(map));
		}

		return boards;
	}

	public int getArticlesCountByBoardId(int boardId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		sql.append("WHERE boardId = ?", boardId);

		return MysqlUtil.selectRowIntValue(sql);
	}


	public List<Article> getArticlesByBoardId(int boardId) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE boardId=?",boardId);
		sql.append("ORDER BY id DESC");

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));

		}
		return articles;
	}


	public String getBoardCodeById(int boardId) {
		SecSql sql = new SecSql();
		sql.append("SELECT code");
		sql.append("FROM board");
		sql.append("WHERE id=?",boardId);

		return MysqlUtil.selectRowStringValue(sql);
	}

	public int getArticlesCount() {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");

		return MysqlUtil.selectRowIntValue(sql);
	}

	public int getArticlesHitCount() {
		SecSql sql = new SecSql();
		sql.append("SELECT SUM(hit)");
		sql.append("FROM article");

		return MysqlUtil.selectRowIntValue(sql);
	}

	public int getBoardArticlesHitCountByBoardId(int boardId) {
		SecSql sql = new SecSql();
		sql.append("SELECT SUM(hit)");
		sql.append("FROM article");
		sql.append("WHERE boardId = ?", boardId);

		return MysqlUtil.selectRowIntValue(sql);
	}



}
