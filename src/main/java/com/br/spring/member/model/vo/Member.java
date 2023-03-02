package com.br.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 * 1) 라이브러리 다운 (pom.xml)
 * 2) 다운로드된 jar찾아서 설치(작업중인 IDE설치) => java -jar xxxxx.jar
 * 3) IDE 재실행
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
	//private int age;
	private String age;
	private String phone;
	private String address;
	private String profileImg;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
	//private String uName; => private String userName;
	// 단, 롬복을 쓸때는 필드명 작성시 적어도 소문자 두글자 이상으로 시작할 것
	
}
