package com.kwj.JAM.dao;

import java.sql.Connection;

import com.kwj.JAM.util.DBUtil;
import com.kwj.JAM.util.SecSql;

public class MemberDao {
	
	SecSql sql = new SecSql();

	

	public boolean dojoin(String loginId, Connection conn) {
		
		sql.append("SELECT COUNT(id) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	
		
	}



}
