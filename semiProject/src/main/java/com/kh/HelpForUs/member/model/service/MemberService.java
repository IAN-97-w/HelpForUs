package com.kh.HelpForUs.member.model.service;

import java.util.HashMap;
import java.util.Map;

import com.kh.HelpForUs.member.model.vo.Member;

public interface MemberService {

	int enroll(Member member);

	Member login(Member member);

	int updateRose(Map<String, Object> map);

}
