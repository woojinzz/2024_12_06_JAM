package com.kwj.JAM.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnecorTest {
	 public static void main(String[] args) {
	        // 데이터베이스 연결 정보
	        String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB URL
	        String user = "root"; // DB 사용자 이름
	        String password = "qwer"; // DB 비밀번호

	        // Connection 객체 선언
	        Connection connection = null;

	        try {
	            // 드라이버 로드 (필수는 아니지만, 명시적으로 작성 가능) cj로 빨간줄 제거 가능
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 데이터베이스 연결
	            connection = DriverManager.getConnection(url, user, password);
	            System.out.println("데이터베이스 연결 성공!");

	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
	        } catch (SQLException e) {
	            System.out.println("데이터베이스 연결 실패: " + e.getMessage());
	        } finally {
	            // 연결 해제
	            if (connection != null) {
	                try {
	                    connection.close();
	                    System.out.println("데이터베이스 연결 해제 완료.");
	                } catch (SQLException e) {
	                    System.out.println("연결 해제 중 오류 발생: " + e.getMessage());
	                }
	            }
	        }
	    }
}