package com.javaex.comtroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
       
    
    
    //메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//user에서 action을 꺼낸다
		String action = request.getParameter("action");
		System.out.println("action파라미터 = " + action); // 입력된 파라미터 그대로 출력
		
		
		if("joinForm".equals(action)) {//회원가입폼
			System.out.println("UserController>joinForm진입");//진입성공
			// 회원가입 폼으로 포워딩하기
			WebUtil.forward(request, response, "./WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)) {//회원가입
			/*
		 	1. 파라미터꺼내기x4
			2. Vo로 만들기
			3. Vo주소만 Dao로 넘기기
			4. Dao에서 insert하기
			5. joinOK로 보내기
			 */
			System.out.println("UserController>join진입"); // 진입성공
			
			//1. 파라미터꺼내기x4
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			System.out.println(id);
			System.out.println(password);
			System.out.println(name);
			System.out.println(gender);
			
			//2. Vo로 만들기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			
			
			//3. Vo주소만 Dao로 넘기기
			UserDao userDao = new UserDao();
			//4. Dao에서 insert하기
			userDao.insert(userVo);

			//5. joinOK로 보내기
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if ("loginForm".equals(action)) { // 로그인 폼
			System.out.println("UserController>LoginForm진입");
			
			//포워딩
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if ("login".equals(action)) { // 로그인
			System.out.println("UserController>Login진입");
			
			//파라미터꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			//Vo만들기
			UserVo userVo = new UserVo(id, password);
			
			//dao
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); // id, password로 유저를 찾은다음에, 유저정보 전체를 불러오는 메소드
			System.out.println(userVo.toString());
			//authUser 주소있으면 로그인 성공
			//authUser 주소없으면 로그인 실패
			if(authUser == null) {
				System.out.println("로그인 실패");
				WebUtil.redirect(request, response, "/mysite2/main");
			}else {
				System.out.println("로그인 성공");
				//성공 시 세션에 id추가
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				//메인 리다이랙트
				WebUtil.redirect(request, response, "/mysite2/main");
			}
		}else if("logout".equals(action)) {//로그아웃
			System.out.println("UserController>Logout진입");
			
			//세션값 지운다
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			System.out.println("로그아웃 완료");
			
			//메인 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}else if("modifyForm".equals(action)) {
			System.out.println("UserController>modifyForm진입");
			
			//로그인한 사람의 no를 세션에서 불러오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//no로 모든정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			System.out.println("userVo : " + userVo);
			//attribut에 Vo담아서 회원정보수정 폼으로 포워딩
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			System.out.println("UserController>modify진입");
			
			//세션에서 no 그대로 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			
			
			//1.파라미터꺼내기
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//2.Vo만들기
			UserVo userVo = new UserVo(no, password, name, gender);
			/*
			userVo.setNo(no);
			userVo.setPassword(password);
			userVo.setName(name);
			userVo.setGender(gender);
			*/
			
			System.out.println(userVo.toString());
			//3.Dao로 넘기기
			UserDao userDao = new UserDao();
			userDao.update(userVo);
			
			//4.리다이렉팅
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
