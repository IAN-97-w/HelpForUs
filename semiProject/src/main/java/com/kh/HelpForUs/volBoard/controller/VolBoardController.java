package com.kh.HelpForUs.volBoard.controller;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.HelpForUs.common.exception.BoardException;
import com.kh.HelpForUs.common.model.service.CommonService;
import com.kh.HelpForUs.common.vo.Attachment;
import com.kh.HelpForUs.common.vo.Cheer;
import com.kh.HelpForUs.common.vo.Image;
import com.kh.HelpForUs.common.vo.PageInfo;
import com.kh.HelpForUs.common.vo.Pagination;
import com.kh.HelpForUs.common.vo.Reply;
import com.kh.HelpForUs.member.model.vo.Member;
import com.kh.HelpForUs.volBoard.model.service.VolBoardService;
import com.kh.HelpForUs.volBoard.model.vo.Application;
import com.kh.HelpForUs.volBoard.model.vo.VolBoard;

@Controller
public class VolBoardController {
	
	@Autowired
	private VolBoardService vService;
	
	@Autowired
	private CommonService cService;
	
	// 봉사 게시글 리스트
	@RequestMapping("volBoardList.vo")
	public String volBoardList(@RequestParam(value="page", required=false) Integer page, Model model, @RequestParam(value="category", required=false) Integer cate,
							   @RequestParam(value="search", required=false) String search) {
		
		int category = 0;
		if(cate != null && cate > 0 && cate < 7) {
			category = cate;
		}
		
		int currentPage = 1;
		if(page != null && page > 1) {
			currentPage = page;
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("category", category);
		map.put("search", search);
		
		int listCount = vService.getVListCount(map);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 9);
		ArrayList<VolBoard> vList = vService.selectVolBoardList(pi, map);
		ArrayList<Attachment> aList = vService.selectAttmList();
		
		if(vList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("vList", vList);
			model.addAttribute("aList", aList);
			model.addAttribute("category", category);
			return "boardListVol";
		} else {
			throw new BoardException("봉사 게시글 조회 실패");
		}
	}
	
	// 봉사 게시글글 작성 페이지 이동
	@RequestMapping("writeVolBoardView.vo")
	public String writeVolBoardView() {
		return "volBoardWrite";
	}
	
	// 봉사 게시글 insert
	@RequestMapping("writeVolBoard.vo")
	public String insertVolBoard(HttpServletRequest request, @ModelAttribute VolBoard vBoard, @RequestParam("file") ArrayList<MultipartFile> files) {
		
		
		String id = ((Member)request.getSession().getAttribute("loginUser")).getMemberUsername();
		vBoard.setRefMemberUsername(id);
		int boardResult = vService.insertVolBoard(vBoard);
		
		ArrayList<Attachment> list = new ArrayList<>();
		for(MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			if(!fileName.equals("")) {
				String fileType = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
				System.out.println(fileType);
				if(fileType.equals("png") || fileType.equals("jpg") || fileType.equals("gif") || fileType.equals("jpeg") || fileType.equals("jfif")) {
					String[] returnArr = saveFile(file, request);
					
					if(returnArr[1] != null) {
						Attachment attm = new Attachment();
						attm.setOriginalName(file.getOriginalFilename());
						attm.setRenameName(returnArr[1]);
						attm.setFileLink(returnArr[0]);
						
						list.add(attm);
					}
				}
			}
		}
		
		for(int i = 0; i < list.size(); i++) {
			Attachment a = list.get(i);
			if(i == 0) {
				a.setLevel(0);
			} else {
				a.setLevel(1);
			}
			
			if(vBoard.getBoardType().equals("Vol")) {
				a.setFileType("Vol");
			}else {
				a.setFileType("volRev");
			}
			
		}
		
		int attmResult = 0;
		
		if(!list.isEmpty()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("bId", 0);

			attmResult = vService.insertAttm(map);
		}
		
		if(boardResult + attmResult == list.size()*2+3) {
			if(vBoard.getBoardType().equals("Vol")) {
				return "redirect:volBoardList.vo";
			} else{
				return "redirect:revBoardList.re";
			}
		} else {
			for(Attachment a : list) {
				deleteFile(a.getRenameName(), request);
			}
			throw new BoardException("봉사 게시글 작성 실패");
		}
	}

	private String[] saveFile(MultipartFile file, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHss");
		int ranNum = (int)(Math.random()*1000);
		String originFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date(System.currentTimeMillis())) + ranNum
											+ originFileName.substring(originFileName.lastIndexOf("."));
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (Exception e) {
			System.out.println("파일 전송 에러" + e.getMessage());
		}
		
		String[] returnArr = new String[2];
		returnArr[0] = savePath;
		returnArr[1] = renameFileName;
		
		return returnArr;
	}
	
	public void deleteFile(String fileName, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		File f = new File(savePath + "\\" + fileName);
		if(f.exists()) {
			f.delete();
		}
	}
	
	@RequestMapping("volBoardDetail.vo")
	public String selectvolBoard(@RequestParam("bId") int bId, @RequestParam(value="nickName", required=false) String nickName, HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		String loginNick = null;
		boolean yn = true;
		
		System.out.println(bId);
		System.out.println(nickName);
		
		if(loginUser != null && nickName != null) {
			loginNick = loginUser.getMemberNickname();
			
			if(loginNick.equals(nickName)) {
				yn = false;
			}
		}
		
		Cheer ch = new Cheer();
		Cheer cheer = null;
		
		if(loginUser != null) {
			ch.setBoardId(bId);
			ch.setMemberUserName(loginUser.getMemberUsername());
			cheer = vService.selectCheer(ch);
		}
		
		VolBoard vBoard = vService.selectVolBoard(bId, yn);
		ArrayList<Attachment> aList = vService.selectAttm(bId);
		ArrayList<Application> app = vService.selectApp(bId);
		
		int appCheck = 0;
		
		if(loginUser != null) {
			for(Application a : app) {
				if(loginUser.getMemberUsername().equals(a.getMemberUserName())) {
					appCheck = 1;
					break;
				}
			}
		}
		
		ArrayList<Reply> rList = cService.selectReply(bId);
		
		if(vBoard != null) {
			vBoard.setVolunteerCurrentPeople(app.size());
			model.addAttribute("vBoard", vBoard);
			model.addAttribute("aList", aList);
			model.addAttribute("cheer", cheer);
			model.addAttribute("appCheck", appCheck);
			model.addAttribute("rList", rList);
			
			if(vBoard.getBoardType().equals("Vol")) {
				return "boardDetailVol";
			}else {
				return "../revBoard/volRevDetail";
			}
		
		} else {
			throw new BoardException("봉사 게시글 조회 실패.");
		}
	}
	
	// 응원하기
	@RequestMapping("cheerBoard.vo")
	public String cheerBoard(@RequestParam("boardId") int boardId, HttpSession session, Model model) {
		String userName = ((Member)session.getAttribute("loginUser")).getMemberUsername();
		Cheer ch = new Cheer(boardId, userName);
		
		int result = vService.cheerBoard(ch);
		
		if(result > 0) {
			model.addAttribute("bId", boardId);
			return "redirect:volBoardDetail.vo";
		} else {
			throw new BoardException("응원하기에 실패했습니다.");
		}
	}
	
	// 응원취소
	@RequestMapping("cheerCancle.vo")
	public String cheerCancle(@RequestParam("boardId") int boardId, HttpSession session, Model model) {
		String userName = ((Member)session.getAttribute("loginUser")).getMemberUsername();
		Cheer ch = new Cheer(boardId, userName);
		
		int result = vService.cheerCancle(ch);
		
		if(result > 0) {
			model.addAttribute("bId", boardId);
			return "redirect:volBoardDetail.vo";
		} else {
			throw new BoardException("응원취소에 실패했습니다.");
		}
	}
	
	// 봉사 게시글 수정페이지 이동
	@RequestMapping("updateVolBoardView.vo")
	public String updateVolBoardView(@RequestParam("bId") int bId, Model model) {
		VolBoard vBoard = vService.selectVolBoard(bId, false);
		ArrayList<Attachment> aList = vService.selectAttm(bId);
		
		if(vBoard != null) {
			model.addAttribute("vBoard", vBoard);
			model.addAttribute("aList", aList);
			return "volBoardEdit";
		} else {
			throw new BoardException("봉사 게시글 조회 실패.");
		}
	}
	
	// 봉사 게시글 수정
	@RequestMapping("updateVolBoard.vo")
	public String updateVolBoard(@ModelAttribute VolBoard v, @RequestParam(value="deleteAttm", required = false) String[] deleteAttm, 
								 @RequestParam("file") ArrayList<MultipartFile> files, HttpServletRequest request, Model model) {
		
		System.out.println(v);
		System.out.println(deleteAttm);
		System.out.println(files);
		
		int boardResult = vService.updateVolBoard(v);
		
		// 새파일 저장
		ArrayList<Attachment> list = new ArrayList<>();
		for(MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			if(!fileName.equals("")) {
				String fileType = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
				if(fileType.equals("png") || fileType.equals("jpg") || fileType.equals("gif") || fileType.equals("jpeg")) {
					String[] returnArr = saveFile(file, request);
					
					if(returnArr[1] != null) {
						Attachment attm = new Attachment();
						attm.setOriginalName(file.getOriginalFilename());
						attm.setRenameName(returnArr[1]);
						attm.setFileLink(returnArr[0]);
						
						list.add(attm);
					}
				}
			}
		}
		
		// 선택한 파일들 삭제
		ArrayList<String> delRename = new ArrayList<>();
		ArrayList<Integer> delLevel = new ArrayList<>();
		
		if(deleteAttm != null) {
			for (String rename : deleteAttm) {
				if (!rename.equals("")) {
					String[] split = rename.split("/");
					delRename.add(split[0]);
					delLevel.add(Integer.parseInt(split[1]));
					
					Image img = new Image(v.getBoardId(), Integer.parseInt(split[2]));
					vService.deleteImage(img);
				}
			}
		}
		
		int deleteAttmResult = 0;
		boolean existBeforeAttm = true;  // 기존 파일이 남아 있는지 확인
		
		if(!delRename.isEmpty()) {
			deleteAttmResult = vService.deleteAttm(delRename);
			if(deleteAttmResult > 0) {
				for(String rename : delRename) {
					deleteFile(rename, request);
				}
			}
			
			if(delRename.size() == deleteAttm.length) { // 기존 파일을 전부 삭제하겠다고 한 경우
				existBeforeAttm = false;
			} else {
				for(int level : delLevel) {
					if(level == 0) {
						vService.updateAttmLevel(v.getBoardId());
						break;
					}
				}
			}
		}
		
		if(deleteAttm == null) {
			existBeforeAttm = false;
		}
		
		for(int i = 0; i < list.size(); i++) {
			Attachment a = list.get(i);
			
			if(existBeforeAttm) {
				a.setLevel(1);
			} else {
				if(i == 0) {
					a.setLevel(0);
				} else {
					a.setLevel(1);
				}
			}
			a.setFileType("Vol");
		}
		
		int attmResult = 0;
		
		if(!list.isEmpty()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("bId", v.getBoardId());
			
			
			attmResult = vService.insertAttm(map);
		}
		
		if(boardResult + attmResult == list.size()*2+3) {
			model.addAttribute("bId", v.getBoardId());
			return "redirect:volBoardDetail.vo";
		} else {
			for(Attachment a : list) {
				deleteFile(a.getRenameName(), request);
			}
			throw new BoardException("봉사 게시글 작성 실패");
		}
	}
	
	// 봉사 게시글 삭제
	@RequestMapping("deleteVolBoard.vo")
	public String deleteVolBoard(@RequestParam("bId") int bId, @RequestParam(value="boardType", required = false) String boardType) {
		int result = vService.deleteBoard(bId);
		result += vService.deleteAttmStatus(bId);
		System.out.println(boardType);
		if(result > 0) {
			if(boardType.equals("volRev")) {
				return "redirect:revBoardList.re";
			}else {
				return "redirect:volBoardList.vo";
			}
			
		} else {
			throw new BoardException("봉사 게시글 삭제 실패");
		}
	}
	
	// 봉사 신청
	@RequestMapping("applicationVol.vo")
	public String applicationVol(@RequestParam("bId") int bId, HttpSession session, Model model) {
		String userName = ((Member)session.getAttribute("loginUser")).getMemberUsername();
		Application app = new Application();
		
		app.setBoardId(bId);
		app.setMemberUserName(userName);
		
		int result = vService.applicationVol(app);
		
		if(result > 0) {
			model.addAttribute("bId", bId);
			return "redirect:volBoardDetail.vo";
		} else {
			throw new BoardException("봉사 신청 실패");
		}
	}
	
	// 봉사 신청 취소
	@RequestMapping("applicationVolCancle.vo")
	public String applicationVolCancle(@RequestParam("bId") int bId, HttpSession session, Model model) {
		String userName = ((Member)session.getAttribute("loginUser")).getMemberUsername();
		Application app = new Application();
		
		app.setBoardId(bId);
		app.setMemberUserName(userName);
		
		int result = vService.applicationVolCancle(app);
		
		if(result > 0) {
			model.addAttribute("bId", bId);
			return "redirect:volBoardDetail.vo";
		} else {
			throw new BoardException("봉사 신청 실패");
		}
	}
		
//	// 문의 팝업
//	@RequestMapping("inquiryVolView.vo")
//	public String inquiryVolView(@RequestParam("bId") int bId, @RequestParam("writer") String writer, Model model) {
//		model.addAttribute("bId", bId);
//		model.addAttribute("writer", writer);
//		
//		return "writeInquiryVol";
//	}
//	
//	// 문의 하기
//	@RequestMapping("inquiryVol.vo")
//	@ResponseBody
//	public int inquiryVol(@ModelAttribute Message msg, HttpSession session) {
//		msg.setSenderUsername(((Member)session.getAttribute("loginUser")).getMemberUsername());
//		System.out.println(msg);
//		int result = vService.inquiryVol(msg);
//		
//		if(result > 0) {
//			return result;
//		} else {
//			throw new BoardException("문의 실패");
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
