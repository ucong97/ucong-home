package com.sbs.example.ucong.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			
			String sql = "UPDATE article SET updateDate = NOW() WHERE id= 3";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.execute();
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

}
