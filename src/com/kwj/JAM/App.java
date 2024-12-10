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

public class App {

	private final String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB
	private final String user = "root"; // 3DB 사용자 이름
	private final String password = "qwer"; // DB 비밀번호

	public void run() {

		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		int lastArticleId = 1;
		// 객체 선언
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			
			while (true) {
				System.out.print("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}

				if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");

					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					try {

						String slq = "INSERT INTO article";
						slq += " SET regDate = NOW()";
						slq += ", updateDate = NOW()";
						slq += ", title = '" + title + "'";
						slq += ", `body` = '" + body + "';";

						pstmt = conn.prepareStatement(slq);
						pstmt.executeUpdate();

						System.out.println(lastArticleId + "번 글이 작성되었습니다.");
						lastArticleId++;
						continue;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				else if (cmd.equals("article list")) {
					System.out.println("== 게시글 목록 ==");

					List<Article> articles = new ArrayList<>();
					ResultSet rs = null;

					try {

						String slq = "SELECT * FROM article";
						slq += " ORDER BY id DESC;";

						pstmt = conn.prepareStatement(slq);
						rs = pstmt.executeQuery();

						while (rs.next()) {

							int id = rs.getInt("id");
							String regDate = rs.getString("regDate");
							String updateDate = rs.getString("updateDate");
							String title = rs.getString("title");
							String body = rs.getString("body");

							Article article = new Article(id, regDate, updateDate, title, body);
							articles.add(article);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						if (rs != null) {
							try {
								rs.close();
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}

					if (articles.size() == 0) {
						System.out.println("작성된 게시글이 없습니다.");
						continue;
					}

					System.out.println("번호	|	제목");

					for (Article article : articles) {
						System.out.printf("%d	|	%s\n", article.id, article.title);
					}
					continue;
				}

				else if (cmd.startsWith("article modify ")) {
					System.out.println("== 게시물 수정 ==");

					ResultSet rs = null;
					int id = Integer.parseInt(cmd.split(" ")[2]);

					System.out.print("수정할 제목) ");
					String title = sc.nextLine();
					System.out.print("수정할 내용) ");
					String body = sc.nextLine();

					if (id == 0) {
						System.out.println("명령어를 다시 입력해 주세요.");
						continue;
					}

					try {

						String slq = "UPDATE article";
						slq += " SET title = '" + title + "'";
						slq += " , `body` = '" + body + "'";
						slq += " , updateDate = NOW()";
						slq += " WHERE id = " + id + ";";

						pstmt = conn.prepareStatement(slq);
						pstmt.executeUpdate();

					} catch (SQLException e) {
						System.out.println("데이터베이스 연결 실패: " + e.getMessage());
					}
					System.out.printf("%d 번 게시물이 수정되었습니다. \n", id);
					continue;
				}

				else {
					System.out.println("명령어를 다시 입력해 주세요.");
					continue;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		sc.close();
		System.out.println("== 프로그램 종료 ==");
	}

}
