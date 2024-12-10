package com.kwj.JAM.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kwj.JAM.dto.Article;


public class JDBCSelectTest {
	 public static void main(String[] args) {
	        // 데이터베이스 연결 정보
	        String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB URL
	        String user = "root"; // DB 사용자 이름
	        String password = "qwer"; // DB 비밀번호

	        // Connection 객체 선언
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        List<Article> articles = new ArrayList<>();

	        try {
	            // 데이터베이스 연결
	            conn = DriverManager.getConnection(url, user, password);
	            
	            String slq = "SELECT * FROM article";
	            slq += " ORDER BY id DESC;";

	            pstmt = conn.prepareStatement(slq);
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {
	            	
	            	int id = rs.getInt("id");
	            	String regDate = rs.getString("regDate"); 
	            	String updateDate = rs.getString("updateDate"); 
	            	String title = rs.getString("title"); 
	            	String body = rs.getString("body"); 
	            	
	            	Article article = new Article(id, regDate, updateDate, title, body);
	            	articles.add(article);
	            	
	            }
	           
	        }  catch (SQLException e) {
	            System.out.println("데이터베이스 연결 실패: " + e.getMessage());
	        } finally {
	            // 연결 해제 / 자원은 실행 역순으로 끄기
	            if (rs != null) {
	                try {
	                	rs.close();
	                } catch (SQLException e) {
	                	e.printStackTrace();
	                }
	            }
	            // 연결 해제 
	            if (pstmt != null) {
	            	try {
	            		pstmt.close();
	            	} catch (SQLException e) {
	            		e.printStackTrace();
	            	}
	            }
	            // 연결 해제
	            if (conn != null) {
	                try {
	                	conn.close();
	                } catch (SQLException e) {
	                	e.printStackTrace();
	                }
	            }
	        }
	    }
}