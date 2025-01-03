package com.kwj.JAM.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kwj.JAM.dao.ArticleDao;
import com.kwj.JAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);

	}

	public int doWrite(String title, String body) {
		return this.articleDao.doWrite(title, body);
	}

	public void doModify(String title, String body, int id) {
		this.articleDao.doModify(title, body, id);

	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public int getArticleCount(int id) {
		return this.articleDao.getArticleCount(id);
	}

	public List<Article> showList() {
		List<Map<String, Object>> articleListMap = articleDao.showList();

		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		return articles;

	}

	public Article showDetail(int id) {
		
		Map<String, Object> articleMap = articleDao.showDetail(id);
		
		if (articleMap.isEmpty()) {
			return null;
		}
		return new Article(articleMap);

	}

	public int getCmdNum(String cmd) {
		int id = 0;
		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			return -1;
		} 
		return id;
	}

}
