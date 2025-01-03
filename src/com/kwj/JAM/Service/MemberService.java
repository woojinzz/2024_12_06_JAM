package com.kwj.JAM.Service;

import java.sql.Connection;
import java.util.Map;

import com.kwj.JAM.dao.MemberDao;
import com.kwj.JAM.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
	}

	public boolean doLoginChk(String loginId) {
		return memberDao.doLoginChk(loginId);
	}

	public void dojoin(String loginId, String loginPw, String name) {
		memberDao.dojoin(loginId, loginPw, name);

	}

	public Member getMemberByLoginId(String loginId) {
		Map<String, Object> memberMap = memberDao.getMemberByLoginId(loginId);
		if (memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);

	}

}
