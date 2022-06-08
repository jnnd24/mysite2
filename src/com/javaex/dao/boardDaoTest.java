package com.javaex.dao;

public class boardDaoTest {

	public static void main(String[] args) {
		

		BoardDao boardDao = new BoardDao();
		/*
		//getBoardList 테스트 
		System.out.println(boardDao.getBoardList());
		//--성공
		
		List<BoardVo> boardList = boardDao.getBoardList();
		//시간표시 테스트
		System.out.println(boardList.get(1).getRegDate());
		
		
		BoardVo getBoard = boardDao.getBoard(3);
		System.out.println(getBoard);
		*/
		System.out.println(boardDao.getUsername(8));
		
	}

}
