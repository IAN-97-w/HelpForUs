package com.kh.HelpForUs.revBoard.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.HelpForUs.common.vo.Attachment;
import com.kh.HelpForUs.common.vo.PageInfo;
import com.kh.HelpForUs.member.model.vo.Donation;
import com.kh.HelpForUs.revBoard.model.vo.RevBoard;
import com.kh.HelpForUs.volBoard.model.vo.VolBoard;

public interface RevBoardService {


	int insertRevBoard(RevBoard rBoard);
	
	int insertAttm(Attachment a);

	int insertImg(int i);

	int getRListCount();

	ArrayList<VolBoard> selectVolBoardList(PageInfo pi, HashMap<String, Object> map);

	ArrayList<Attachment> selectAttmList();

	ArrayList<Donation> selectDonor(int bId);


	

	

/*	int insertRevBoard(RevBoard rBoard);

	int insertAttm(Attachment a);

	int insertImg(int i);

	int getRListCount();

	ArrayList<RevBoard> selectRevBoardList(PageInfo pi, int category);

	ArrayList<Attachment> selectAttmList();
*/
	
	
}
