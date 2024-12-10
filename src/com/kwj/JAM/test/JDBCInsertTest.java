package com.kwj.JAM.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
	 public static void main(String[] args) {
	        // 데이터베이스 연결 정보
	        String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB URL
	        String user = "root"; // 3DB 사용자 이름
	        String password = "qwer"; // DB 비밀번호

	        // Connection 객체 선언
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            // 데이터베이스 연결
	            conn = DriverManager.getConnection(url, user, password);
	            
	            String slq = "INSERT INTO article";
	            slq += " SET regDate = NOW()";
	            slq += ", updateDate = NOW()";
	            slq += ", title = '제목1'";
	            slq += ", `body` = '내용1';";
	            
	            pstmt = conn.prepareStatement(slq);
	            pstmt.executeUpdate();
	           
	        }  catch (SQLException e) {
	            System.out.println("데이터베이스 연결 실패: " + e.getMessage());
	        } finally {
	            // 연결 해제 / 자원은 실행 역순으로 끄기
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