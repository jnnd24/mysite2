package com.javaex.comtroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//포스트에서 한글꺠지는 현상 방지
		request.setCharacterEncoding("UTF-8");
		
		//action 파라 설정
		String action = request.getParameter("action");
		System.out.println("action파라미터 = " + action); // 입력된 파라미터 그대로 출력
		
		if("list".equals(action)) {
			
			//보드리스트 가져오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//어트리뷰트로 보내주기
			request.setAttribute("boardList", boardList);
			
			//포워딩
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		//보기
		}else if("read".equals(action)) {
			System.out.println("read진입");//진입성공
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
		//삭제
		}else if("delete".equals(action)) {
			System.out.println("delete진입");//진입성공
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);
			
			WebUtil.redirect(request, response, "./board?action=list");
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
