package com.kh.HelpForUs.volBoard.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.HelpForUs.common.vo.Attachment;
import com.kh.HelpForUs.common.vo.Cheer;
import com.kh.HelpForUs.common.vo.Image;
import com.kh.HelpForUs.common.vo.PageInfo;
import com.kh.HelpForUs.member.model.vo.Message;
import com.kh.HelpForUs.volBoard.model.vo.Application;
import com.kh.HelpForUs.volBoard.model.vo.VolBoard;

public interface VolBoardService {

	int insertVolBoard(VolBoard vBoard);

	int insertAttm(HashMap<String, Object> map);

	int getVListCount(HashMap<String, Object> map);

	ArrayList<VolBoard> selectVolBoardList(PageInfo pi, HashMap<String, Object> map);

	ArrayList<Attachment> selectAttmList();

	VolBoard selectVolBoard(int bId, boolean yn);

	ArrayList<Attachment> selectAttm(int bId);

	Cheer selectCheer(Cheer ch);

	int cheerBoard(Cheer ch);

	int cheerCancle(Cheer ch);

	int updateVolBoard(VolBoard v);

	int deleteAttm(ArrayList<String> delRename);

	void updateAttmLevel(int boardId);

	void deleteImage(Image img);

	int deleteBoard(int bId);

	int deleteAttmStatus(int bId);

	ArrayList<Application> selectApp(int bId);

	int applicationVol(Application app);

	int applicationVolCancle(Application app);

//	int inquiryVol(Message msg);

}
