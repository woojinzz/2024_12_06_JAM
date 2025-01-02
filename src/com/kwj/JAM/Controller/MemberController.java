package com.kwj.JAM.Controller;

import java.sql.Connection;
import java.util.Scanner;

import com.kwj.JAM.Service.MemberService;
import com.kwj.JAM.util.DBUtil;
import com.kwj.JAM.util.SecSql;

public class MemberController{
	
	private Connection conn;
	private Scanner sc;
	
	MemberService memberService = new MemberService();
	
	
	
	public MemberController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void doJoin() {
		
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
			
			boolean isLoginIdDup = memberService.dojoin(loginId, conn);
			
			
			if (isLoginIdDup) {
				System.out.printf("[ %s ] 는 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}
			System.out.printf("[ %s ]는 사용 가능한 아이디입니다.\n", loginId);
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

}
