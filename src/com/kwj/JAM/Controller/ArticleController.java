package com.kwj.JAM.Controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.kwj.JAM.dto.Article;
import com.kwj.JAM.util.DBUtil;
import com.kwj.JAM.util.SecSql;

public class ArticleController {
	
	private Connection conn;
	private Scanner sc;

	public ArticleController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc  = sc;
	
	}

	public void doWrite() {
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

	public void showList() {
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
			return;
		}

		System.out.println("번호	|		제목		| 		작성일		");

		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	\n", article.id, article.title, article.regDate);
		}
	}

	public void showDetail(String cmd) {
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시믈은 존재하지 않습니다.\n", id);
			return;
		}
		
		Article article = new Article(articleMap);
		
		System.out.println("== 게시물 상세보기 ==");
		
		System.out.printf("번호 : %d \n", article.id);
		System.out.printf("작성일 : %s \n", article.regDate);
		System.out.printf("수정일 : %s \n", article.updateDate);
		System.out.printf("제목 : %s \n", article.title);
		System.out.printf("내용 : %s \n", article.body);
		
	}

	public void doModify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		int articleCount = DBUtil.selectRowIntValue(conn, sql);
		
		if(articleCount == 0) {
			System.out.printf("%d번 게시글이 없습니다.\n", id);
			return;
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
	}

}
