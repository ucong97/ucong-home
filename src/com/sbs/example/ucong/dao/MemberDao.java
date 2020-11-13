package com.sbs.example.ucong.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sbs.example.ucong.dto.Member;

public class MemberDao {

	public void join(String loginId, String loginPw, String name) {
		Connection con = null;
		try {
			String url = "jdbc:mysql://localhost:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pass = "sbs123414";
			// 드라이버 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 연결생성
			try {
				con = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "INSERT INTO member SET loginId=?,loginPw=?,name=?";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1,loginId);
				ps.setString(2,loginPw);
				ps.setString(3,name);
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Member getMemberByLoginId(String loginId) {
		Connection con = null;
		Member member=null;
		try {
			String url = "jdbc:mysql://localhost:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String user = "sbsst";
			String pass = "sbs123414";
			// 드라이버 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 연결생성
			try {
				con = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "SELECT * FROM member WHERE loginId = ?";
			
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1,loginId);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					int id= rs.getInt("id");
					String memberLoginId=rs.getString("loginId");
					String memberLoginPw=rs.getString("loginPw");
					String memberName=rs.getString("name");
					
					member = new Member(id,memberLoginId,memberLoginPw,memberName);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member;
	}

}
