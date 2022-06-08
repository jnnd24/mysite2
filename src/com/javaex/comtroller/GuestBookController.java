package com.javaex.comtroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//포스트에서 한글 꺠지는 현상 방지
		request.setCharacterEncoding("UTF-8");
		
		//
		
		//gbc에서 action을 꺼낸다
		String action = request.getParameter("action");
		System.out.println("action파라미터 = " + action); // 입력된 파라미터 그대로 출력
		
		//addList
		if("addList".equals(action)) {
			GuestBookDao guestbookDao = new GuestBookDao();
			List<GuestBookVo> guestbookList = guestbookDao.getGuestBookList();
			
			//어트리뷰트로 보내기
			request.setAttribute("guestbookList", guestbookList);
			System.out.println(guestbookList);
			
			//addList로 포워딩
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		//add
		}else if("add".equals(action)) {
			//파라미터 잡기
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//Vo로 올리기
			GuestBookVo gestbookVo = new GuestBookVo(name, password, content);
			
			//Dao로 추가하기
			GuestBookDao guestbookDao = new GuestBookDao();
			guestbookDao.insert(gestbookVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "gbc?action=addList");
			
		//deleteForm	
		}else if("deleteForm".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		//delete
		}else if("delete".equals(action)) {
			
			//파라미터 잡기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//Vo에 담기
			GuestBookVo guestbookVo = new GuestBookVo(no, password);

			//Dao로 보내서 삭제하기
			GuestBookDao guestbookDao = new GuestBookDao();
			guestbookDao.delete(guestbookVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "gbc?action=addList");
			
		}
		
		
		
	}

	
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
