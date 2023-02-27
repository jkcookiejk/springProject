package com.br.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.spring.member.model.dao.MemberDao;
import com.br.spring.member.model.vo.Member;

@Service 
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession; //선언만해서 null일것 같지만 나름대로 생성된 객체가 대입될 거임
	
	@Autowired 
	private MemberDao mDao;
	
	@Override
	public Member loginMember(Member m) {
		Member loginUser = mDao.loginMember(sqlSession, m); 
		return loginUser; 
	}

	@Override
	public int insertMember(Member m) {
		int result = mDao.insertMember(sqlSession, m); 
		return result; 
	}

	@Override
	public int updateMember(Member m) {
		return mDao.updateMember(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return mDao.deleteMember(sqlSession, userId);
	}
	

	@Override
	public int idCheck(String checkId) {
		return mDao.idCheck(sqlSession, checkId);  
	
	}

	@Override
	public int updateProfileImg(Member m) {
		return mDao.updateProfile(sqlSession, m); 
	}


}
