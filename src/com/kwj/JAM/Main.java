package com.kwj.JAM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kwj.JAM.dto.Article;

public class Main {
	
	
	public static void main(String[] args) {
		
        String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB URL
        String user = "root"; // 3DB 사용자 이름
        String password = "qwer"; // DB 비밀번호

//        객체 선언
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
		Scanner sc = new Scanner(System.in);
		int lastArticleId  = 1;

		
		System.out.println("== 프로그램 시작 ==");
		
		while(true) {
			System.out.print("명령어) ");
			String cmd = sc.nextLine().trim();
			
			if (cmd.equals("article write")) {
				System.out.println("== 게시물 작성 ==");
				
				System.out.print("제목) ");
				String title = sc.nextLine();
				System.out.print("내용) ");
				String body = sc.nextLine();
				
		        try {
		            // 데이터베이스 연결
		            conn = DriverManager.getConnection(url, user, password);
		            
		            String slq = "INSERT INTO article";
		            slq += " SET regDate = NOW()";
		            slq += ", updateDate = NOW()";
		            slq += ", title = '" + title + "'";
		            slq += ", `body` = '" + body + "';";
		            
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
		        
//		        Article article = new Article(lastArticleId, title, body);
//		        articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 작성되었습니다.");
				lastArticleId++;
				continue;
			}
			
			else if (cmd.equals("article list")) {
				System.out.println("== 게시글 목록 ==");
				
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
				
				if (articles.size() == 0) {
					System.out.println("작성된 게시글이 없습니다.");
					continue;
				}
				
				System.out.println("번호	|	제목");
			
				for(Article article : articles) {
					System.out.printf("%d	|	%s\n", article.id, article.title);
				}
				continue;
			}
			
			if (cmd.equals("exit")) {
				break;
			}
			
			sc.close();
			System.out.println("== 프로그램 종료 ==");
			
			
		}
	}


}
