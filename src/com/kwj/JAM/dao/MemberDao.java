package com.kwj.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.kwj.JAM.util.DBUtil;
import com.kwj.JAM.util.SecSql;

public class MemberDao {
	
	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	public boolean doLoginChk(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void dojoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", name = ?", name);	
		DBUtil.insert(conn, sql);
	}

	public Map<String, Object> getMemberByLoginId(String loginId) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		return DBUtil.selectRow(conn, sql);
		
	}

}
