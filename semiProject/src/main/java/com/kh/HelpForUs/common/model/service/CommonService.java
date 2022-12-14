package com.kh.HelpForUs.common.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.HelpForUs.common.vo.Attachment;
import com.kh.HelpForUs.common.vo.Board;
import com.kh.HelpForUs.common.vo.PageInfo;
import com.kh.HelpForUs.common.vo.Reply;

public interface CommonService {

	ArrayList<Integer> countRose();

	int countApp();

	ArrayList<Board> latestBoard();

	ArrayList<Attachment> selectAttm();

	ArrayList<Board> seletNot5();

	ArrayList<Board> selectNotBoardList(PageInfo pi, HashMap<String, Object> map);

	int getNListCount();

	Board selectnotBoard(int bId, boolean yn);

	int insertNotBoard(Board b);

	int updateNotBoard(Board b);

	int delelteNotBoard(int bId);

	int insertReply(Reply r);

	ArrayList<Reply> selectReply(int refBoardId);

	int deleteReply(int rId);

}
