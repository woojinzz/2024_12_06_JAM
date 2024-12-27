package com.kwj.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.kwj.JAM.dto.Article;
import com.kwj.JAM.util.DBUtil;
import com.kwj.JAM.util.SecSql;

public class App {

	private final String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB
	private final String user = "root"; // 3DB 사용자 이름
	private final String password = "qwer"; // DB 비밀번호

	public void run() {

		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		// 객체 선언
		Connection conn = null;
//		PreparedStatement pstmt = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			
			while (true) {
				System.out.print("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				
				if (cmd.equals("member join")) {
					System.out.println("== 회원가입 페이지 ==");
					String loginId = null;
					String loginPw = null;
					String loginPwChk = null;
					String name = null;
					
					while(true) {
						
						System.out.print("아이디) ");
						loginId = sc.nextLine().trim();

						if (loginId.length() == 0) {
							System.out.println("아이디는 필수 입력 정보입니다");
							continue;
						}
						
						SecSql sql = new SecSql();
						sql.append("SELECT COUNT(id) > 0");
						sql.append("FROM `member`");
						sql.append("WHERE loginId = ?", loginId);
						
						boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);
						
						if (isLoginIdDup) {
							System.out.printf("[ %s ] 는 이미 사용중인 아이디입니다.\n", loginId);
							continue;
						}
						System.out.printf("[ %s ]는 이미 사용 가능한 아이디입니다.\n", loginId);
						break;
					}
					
					while (true) {
						
						System.out.print("비밀번호) ");
						loginPw = sc.nextLine().trim();
						
						if (loginPw.length() == 0) {
							System.out.println("비밀번호는 필수 입력 정보입니다");
							continue;
						}
						
						System.out.print("비밀번호 확인) ");
						loginPwChk = sc.nextLine().trim();
						
						if (loginPw.equals(loginPwChk) == false) {
							System.out.println("비밀번호가 다릅니다.");
							continue;
						}
						break;
					}
					
					while (true) {
						System.out.print("이름) ");
						name = sc.nextLine().trim();
						
						if (name.length() == 0) {
							System.out.println("이름은 필수 입력 정보입니다.");
							continue;
						}
						break;
						
					}
					
					
					SecSql sql = new SecSql();
					sql.append("INSERT INTO `member`");
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", loginId = ?", loginId);
					sql.append(", loginPw = ?", loginPw);
					sql.append(", name = ?", name);
					
					DBUtil.insert(conn, sql);
					
					System.out.printf("[ %s ] 회원님 가입을 축하합니다.\n", loginId );
					
				}
				

				else if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");

					System.out.print("제목) ");
					String title = sc.nextLine().trim();
					System.out.print("내용) ");
					String body = sc.nextLine().trim();

					SecSql sql = new SecSql();
					sql.append("INSERT INTO article"); 
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);
					
					int id = DBUtil.insert(conn, sql);

					System.out.println(id + "번 글이 작성되었습니다.");
				}

				else if (cmd.equals("article list")) {
					
					List<Article> articles = new ArrayList<>();
					
					SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
					sql.append("ORDER BY id DESC");
					
					List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
					
					for (Map<String, Object> articleMap : articleListMap) {
						articles.add(new Article(articleMap));
					}
					System.out.println("== 게시글 목록 ==");

					if (articles.size() == 0) {
						System.out.println("작성된 게시글이 없습니다.");
						continue;
					}

					System.out.println("번호	|		제목		| 		작성일		");

					for (Article article : articles) {
						System.out.printf("%d	|	%s	|	%s	\n", article.id, article.title, article.regDate);
					}
				}
				
				else if (cmd.startsWith("article detail ")) {
					
					int id = Integer.parseInt(cmd.split(" ")[2]);
					
					SecSql sql = new SecSql();
					sql.append("SELECT *");
					sql.append("FROM article");
					sql.append("WHERE id = ?", id);
					
					Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
					
					if (articleMap.isEmpty()) {
						System.out.printf("%d번 게시믈은 존재하지 않습니다.\n", id);
						continue;
					}
					
					Article article = new Article(articleMap);
					
					System.out.println("== 게시물 상세보기 ==");
					
					System.out.printf("번호 : %d \n", article.id);
					System.out.printf("작성일 : %s \n", article.regDate);
					System.out.printf("수정일 : %s \n", article.updateDate);
					System.out.printf("제목 : %s \n", article.title);
					System.out.printf("내용 : %s \n", article.body);
					
				}

				else if (cmd.startsWith("article modify ")) {
					
					int id = Integer.parseInt(cmd.split(" ")[2]);
					
					SecSql sql = new SecSql();
					sql.append("SELECT COUNT(id)");
					sql.append("FROM article");
					sql.append("WHERE id = ?", id);
					
					int articleCount = DBUtil.selectRowIntValue(conn, sql);
					
					if(articleCount == 0) {
						System.out.printf("%d번 게시글이 없습니다.\n", id);
						continue;
					}
					System.out.println("== 게시물 수정 ==");
					
					System.out.print("수정할 제목) ");
					String title = sc.nextLine();
					System.out.print("수정할 내용) ");
					String body = sc.nextLine();
										
					sql = new SecSql();
					sql.append("UPDATE article");
					sql.append("SET updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);
					sql.append("WHERE id = ?", id);
					
					DBUtil.update(conn, sql);
					
					System.out.printf("%d 번 게시물이 수정되었습니다. \n", id);
					continue;
				}
				
			

				else {
					System.out.println("명령어를 다시 입faf력해 주세요.");
					continue;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
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
