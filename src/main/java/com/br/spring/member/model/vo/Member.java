package com.br.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 * 1) pom.xml에 라이브러리 다운로드
 * 2) 다운로드된 jar파일 실행해서 설치 (작업중인 IDE프로그램에 설치)
 * 
 * @NoArgsConstructor: 매개변수 없는 기본생성자
 * @AllArgsConstructor: 모든 매개변수가 있는 생성자
 * @Setter @Getter: setter, getter메소드
 * @ToString: toString메소드
 * 
 * 필드 수정 시 바로 반영됨
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName; 
	private String email; 
	private String gender; 
	//private int age; --> int형을 String형으로 변경!!
	private String age; 
	private String phone; 
	private String address;
	private String profileImg; 
	private Date enrollDate; 
	private Date modifyDate; 
	private String status; 
	
	//private String uName; => private String userName; 
	//롬복에서는 소문자 하나로 시작해서 바로 대문자가 오는 필드명을 절대로 써서는 안됨!!!!!!!!
	// 단, 롬복을 쓸때는 필드명 작성시 적어도 소문자 두글자 이상으로 시작할 것 
	//절대 한 글자 소문자 뒤에 대문자 바로 와서는 안됨 !!!!!!!!!!!!!!!!!!!!!!!
	
	
}
