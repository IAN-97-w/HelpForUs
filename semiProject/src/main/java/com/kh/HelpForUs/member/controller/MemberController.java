package com.kh.HelpForUs.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.HelpForUs.common.exception.MemberException;
import com.kh.HelpForUs.member.model.service.MemberService;
import com.kh.HelpForUs.member.model.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@RequestMapping("loginView.me")
	public String loginView() {
		return "login";
	}
	
	@RequestMapping("myPage.me")
	public String myPageView() {
		return "mypage";
	}
	
	// 회원가입 작성 이동
	@RequestMapping("enrollView.me")
	public String enrollView(@RequestParam("right") String right,Model model) {
		model.addAttribute("right", right);
		return "enroll";
	}
	
	// 회원가입 일반,단체 선택 이동
	@RequestMapping("enrollButton.me")
	public String enrollButtonView() {
		return "enrollButton";
	}
	
	
	// 회원가입 메서드
	@RequestMapping("enroll.me")
	public String enroll(@ModelAttribute("Member")Member member,@RequestParam("memberPwd2")String memberPwd2){
		if(!member.getMemberPwd().equals(memberPwd2)) {
			throw new MemberException("비밀번호가 일치하지 않습니다.");
		}
		
		String enPwd = bcrypt.encode(member.getMemberPwd());
		member.setMemberPwd(enPwd);
		
		int result = mService.enroll(member);
		
		if(result>0){
			return "redirect:/";
		} else {
			throw new MemberException("회원가입에 실패했습니다.");
		}
	}
	
	// 로그인 메서드
	@RequestMapping("login.me")
	public String login(@ModelAttribute("Member")Member member,HttpSession session) {
		Member loginUser = mService.login(member);
		
		String rawPwd = member.getMemberPwd();
		String enPwd = loginUser.getMemberPwd();
		
		if(bcrypt.matches(rawPwd, enPwd)) {
			session.setMaxInactiveInterval(1800);
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		} else {
			throw new MemberException("로그인에 실패 하셨습니다.");
		}
	}
	
	// 로그아웃 메서드
	@RequestMapping("logout.me")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 내정보 이동 메서드
	@RequestMapping("myInfo.me")
	public String myInfo() {
		return "myinfo";
	}
	
	// 아이디 중복 검사 메서드
	@RequestMapping("checkUesrname.me")
	@ResponseBody
	public String checkUserName(@RequestParam("userName")String userName){
		int count = mService.checkUserName(userName);
		String result = count>0 ? "yes" : "no";
		return result;
	}
	
	// 닉네임 중복 검사 메서드
	@RequestMapping("checkNickName.me")
	@ResponseBody
	public String checkNickName(@RequestParam("nickName")String nickName){
		int count = mService.checkNickName(nickName);
		String result = count>0 ? "yes" : "no";
		return result;
	}
	
	// 회원 정보 수정 메서드
	@RequestMapping("updateInfo.me")
	public String updateInfo(@ModelAttribute Member member,@RequestParam("memberPwd2")String memberPwd2,HttpSession session) {
		if(!member.getMemberPwd().equals(memberPwd2)) {
			throw new MemberException("비밀번호가 일치하지 않습니다.");
		}
		String rawPwd = member.getMemberPwd();
		String enPwd = bcrypt.encode(rawPwd);
		member.setMemberPwd(enPwd);
		
		int result = mService.updateInfo(member);
		
		if(result>0) {
			session.invalidate();
			return "redirect:/";
		} else {
			throw new MemberException("회원 정보 수정에 실패하셨습니다.");
		}
	}
	
	// 회원 탈퇴 메서드
	@RequestMapping("deleteMember")
	public String deleteMember(HttpSession session) {
		String userName = ((Member)session.getAttribute("loginUser")).getMemberUsername();
		int result = mService.deleteMember(userName);
		
		if(result>0) {
			session.invalidate();
			return "redirect:/";
		} else {
			throw new MemberException("회원 탈퇴 실패");
		}
	}
	
	
	
}
