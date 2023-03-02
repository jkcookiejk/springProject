package com.br.spring.ajax.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
	
	private String userId;
	private String userPwd;
	private String userName;
	private int age;
	private String phone;

}
