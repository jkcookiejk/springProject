package com.br.spring.member.model.service;

import com.br.spring.member.model.vo.Member;

public interface MemberService {
	
	// 로그인 서비스 (select)
	Member loginMember(Member m);
	// 회원가입 서비스 (insert)
	int insertMember(Member m);
	// 정보수정 서비스 (update)
	int updateMember(Member m);
	// 회원탈퇴 서비스 (update)
	int deleteMember(String userId);
	
	// 비밀번호변경용 서비스 (update) => Test13에서 진행
	
	
	// 아이디 중복체크 서비스 (select)
	int idCheck(String checkId);
	// 프로필이미지 변경용 서비스 (update)
	int updateProfileImg(Member m);

}
