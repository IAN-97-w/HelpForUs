﻿package com.kh.HelpForUs.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.HelpForUs.member.model.dao.MemberDAO;
import com.kh.HelpForUs.member.model.vo.Member;

@Service("mService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int enroll(Member member) {
		return mDAO.enroll(sqlSession, member);
	}

	@Override
	public Member login(Member member) {
		return mDAO.login(sqlSession,member);
	}

	@Override
	public int updateRose(Map<String, Object> map) {
		return mDAO.updateRose(sqlSession,map);
	}
	
	@Override
	public int checkUserName(String userName) {
		return mDAO.checkUsername(sqlSession, userName);
	}


	@Override
	public int checkNickName(String nickName) {
		return mDAO.checkNickName(sqlSession,nickName);
	}

	@Override
	public int updateInfo(Member member) {
		return mDAO.updateInfo(sqlSession,member);
	}

	@Override
	public int deleteMember(String userName) {
		return mDAO.deleteMember(sqlSession,userName);
	}

	@Override
	public int insertPay(Map<String, Object> map) {
		return mDAO.insertPay(sqlSession,map);
	}

	@Override
	public int getDListCount(String userName) {
		return mDAO.getDListCount(sqlSession, userName);
	}

	@Override
	public int getVListCount(String userName) {
		return mDAO.getVListCount(sqlSession, userName);
	}

	@Override
	public int getGroupDListCount(String userName) {
		return mDAO.getGroupDListCount(sqlSession, userName);
	}

	@Override
	public int getGroupVListCount(String userName) {
		return mDAO.getGroupVListCount(sqlSession, userName);
	}

	@Override
	public int getEndDListCount(String userName) {
		return mDAO.getEndDListCount(sqlSession, userName);
	}

	@Override
	public int getEndVListCount(String userName) {
		return mDAO.getEndVListCount(sqlSession, userName);
	}
}
