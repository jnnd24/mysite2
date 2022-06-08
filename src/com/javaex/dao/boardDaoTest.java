package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public class boardDaoTest {

	public static void main(String[] args) {

		//getBoardList 테스트 
		BoardDao boardDao = new BoardDao();
		System.out.println(boardDao.getBoardList());
		//--성공
		
		List<BoardVo> boardList = boardDao.getBoardList();
		//시간표시 테스트
		System.out.println(boardList.get(1).getRegDate());
		
		
		
	}

}
