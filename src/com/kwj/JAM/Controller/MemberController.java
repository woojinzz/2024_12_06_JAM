package com.kwj.JAM.Controller;

import java.sql.Connection;
import java.util.Scanner;

import com.kwj.JAM.Service.MemberService;
import com.kwj.JAM.dto.Member;

public class MemberController{
	
	private MemberService memberService;
	private Scanner sc;
	private boolean loginChk;
	
	public MemberController(Connection conn, Scanner sc) {
		this.memberService = new MemberService(conn);
		this.sc = sc;
		this.loginChk = false;
		
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
			
			boolean isLoginIdDup = memberService.doLoginChk(loginId);
			
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
	
		memberService.dojoin(loginId, loginPw, name);
		System.out.printf("[ %s ] 회원님 가입을 축하합니다.\n", loginId );
		
	}

	public void doLogin() {

		String loginId = null;
		String loginPw = null;
		
		if (loginChk) {
			System.out.println("로그아웃을 먼저 해주세요.");
			return;
		}
		
		while(true) {
			
			System.out.println("== 로그인 ==");
			System.out.print("아이디) ");
			loginId = sc.nextLine().trim();
			System.out.print("비밀번호) ");
			loginPw = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 값 입니다.");
				continue;
			}
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 값 입니다.");
				continue;
			}
			
			Member member = memberService.getMemberByLoginId(loginId);
			
			if (member == null) {
				System.out.printf("[ %s ] 는 존재하지 않는 아이디 입니다. \n", loginId);
				continue;
			}
			
			if (member.loginPw.equals(loginPw) == false) {
				System.out.printf("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}

		System.out.printf("[ %s] 로그인 되었습니다.");
	
		
	}

	public void dologout(String cmd) {
		
		if (loginChk) {
			loginChk = false; 
			System.out.println("로그아웃 되었습니다.");
		}
		else {
			System.out.println("로그인을 먼저 해주세요.");
		}
		
	
	}

}
