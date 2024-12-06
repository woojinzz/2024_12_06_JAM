package com.kwj.JAM;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kwj.JAM.dto.Article;

public class Main {
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int lastArticleId  = 1;
		List<Article> articles = new ArrayList<>();
		
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
				
				Article article = new Article(lastArticleId, title, body);
				articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 작성되었습니다.");
				lastArticleId++;
				continue;
			}
			
			else if (cmd.equals("article list")) {
				System.out.println("== 게시글 목록 ==");
				if (articles.size() == 0) {
					System.out.println("작성된 게시글이 없습니다.");
					continue;
				}
				
				System.out.println("번호	|	제목");
			
				for(int i = articles.size()-1 ; i >= 0   ; i--) {
					Article article = articles.get(i);
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
