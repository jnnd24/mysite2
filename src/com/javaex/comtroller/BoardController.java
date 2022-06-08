package com.javaex.comtroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

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
			
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));

			//조회수 올리기
			BoardDao boardDao = new BoardDao();
			boardDao.hitup(no);
			
			
			
			//Dao로 게시물 불러오기
			BoardVo getBoard = boardDao.getBoard(no);
			
			//어트리뷰트로 보내주기
			request.setAttribute("getBoard", getBoard);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		
		//삭제
		}else if("delete".equals(action)) {
			System.out.println("delete진입");//진입성공
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);
			
			WebUtil.redirect(request, response, "./board?action=list");
		
		//수정 폼
		}else if("modifyForm".equals(action)) {
			System.out.println("modifyForm진입");//진입성공

			int no = Integer.parseInt(request.getParameter("no"));

			//Dao로 게시물 불러오기
			BoardDao boardDao = new BoardDao();
			BoardVo getBoard = boardDao.getBoard(no);
			
			//어트리뷰트로 보내주기
			request.setAttribute("getBoard", getBoard);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		//수정
		}else if("modify".equals(action)) {
			System.out.println("modify진입");//진입성공

			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//Vo로 만들기
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			//Dao에서 수정하기
			BoardDao boardDao = new BoardDao();
			boardDao.update(boardVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
		
		//등록
		}else if("writeForm".equals(action)) {
			System.out.println("writeForm진입");//진입성공
			
			//로워딩
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("write".equals(action)) {
			System.out.println("write진입");//진입성공
			
			
			//세션에서 로그인정보 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			/*
			//이름 가져오기
			UserVo userVo = boardDao.getUsername(no);
			String name = userVo.getName();
			*/
			
			//파라미터 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//vo로 담아서 보내기
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);

			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "./board?action=list");
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
