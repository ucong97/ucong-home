package com.sbs.example.ucong.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.ucong.dto.Article;

public class ArticleDao {

	public List<Article> getArticles() {
		Connection con = null;
		List<Article> articles = new ArrayList<>();
		try {


			String url = "jdbc:mysql://192.168.0.103:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pass = "sbs123414";

			// 기사등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "SELECT * FROM article ORDER BY id DESC";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String regDate= rs.getString("regDate");
					String updateDate =rs.getString("updateDate");
					String title=rs.getString("title");
					String body=rs.getString("body");
					int memberId = rs.getInt("memberId");
					int boardId= rs.getInt("boardId");
					
					Article article = new Article(id,regDate,updateDate,title,body,memberId,boardId);
					articles.add(article);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} finally {

			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		return articles;
	}

	public boolean getArticleById(int id) {
		Connection con = null;
		try {


			String url = "jdbc:mysql://192.168.0.103:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pass = "sbs123414";

			// 기사등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "DELETE FROM article WHERE id=?";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				
				ps.execute();
				
				return true;
				
			} catch (SQLException e) {
				return false;
			}
			
			
		} finally {

			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public boolean modify(int modifyId, String title, String body) {
		Connection con = null;
		try {


			String url = "jdbc:mysql://192.168.0.103:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pass = "sbs123414";

			// 기사등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// 연결 생성
			try {
				con = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "UPDATE article SET title=?,`body`=?,updateDate = NOW() WHERE id = ?";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, title);
				ps.setString(2, body);
				ps.setInt(3, modifyId);
				
				ps.executeUpdate();
				
				return true;
				
			} catch (SQLException e) {
				return false;
			}
			
			
		} finally {

			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
