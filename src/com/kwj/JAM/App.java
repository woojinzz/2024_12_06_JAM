package com.kwj.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.kwj.JAM.Controller.ArticleController;
import com.kwj.JAM.Controller.MemberController;

public class App {

	private final String url = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // DB
	private final String user = "root"; // 3DB 사용자 이름
	private final String password = "qwer"; // DB 비밀번호


	public void run() {

		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		
	
		try {
			conn = DriverManager.getConnection(url, user, password);
			MemberController memberController = new MemberController(conn, sc);
			ArticleController articleController = new ArticleController(conn, sc);
			
			while (true) {
				System.out.print("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				if (cmd.length() == 0) {
					System.out.println("명령어를 입력해 주세요.");
					continue;
				}
				
				if (cmd.equals("member join")) {
					memberController.doJoin();
				}
				
				else if (cmd.equals("member login")) {
					memberController.doLogin();
				}
				
				else if (cmd.equals("article write")) {
					articleController.doWrite();
				}

				else if (cmd.equals("article list")) {
					articleController.showList();
				}
				
				else if (cmd.startsWith("article detail ")) {
					articleController.showDetail(cmd);
				}

				else if (cmd.startsWith("article modify ")) {
					articleController.doModify(cmd);
				}
				else if (cmd.startsWith("article delete")) {
					articleController.doDelete(cmd);
				}
				else if (cmd.startsWith("member logout")) {
					memberController.dologout(cmd);
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
