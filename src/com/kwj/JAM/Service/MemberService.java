package com.kwj.JAM.Service;

import java.sql.Connection;

import com.kwj.JAM.dao.MemberDao;

public class MemberService {
	
	MemberDao memberDao = new MemberDao();
	


	public boolean dojoin(String loginId, Connection conn) {
		return memberDao.dojoin(loginId, conn);
	}



}
