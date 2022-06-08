package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	
	//필드
	
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//생성자
	
	//메소드gs
	
	//메소드 일반
	//드라이버로딩
	private void getConnection() {
		try { // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
		    // 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
		}catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
	}
	
	//자원정리
	private void close() {
		try {
		if (rs != null) {
            rs.close();
        }                
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
	}

	
	//리스트 불러오기
	public List<BoardVo> getBoardList() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		getConnection();
		
		try {
			//sql준비, 바인딩, 실행
			String query = "";
			query += " select b.no ";
			query += "         ,b.title ";
			query += "         ,b.content ";
			query += "         ,b.hit ";
			query += "         ,to_char(b.reg_date, 'YY-MM-DD HH24:MI') reg_date ";
			query += "         ,b.user_no ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by reg_date desc ";
			
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				
				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				boardList.add(boardVo);
			}
			
		
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return boardList;
		
	}
		
	
	//삭제
	public int delete(int no) {
		int count = 0;
		getConnection();
		
		try {
			//sql문 // 바인딩 // 실행
			String query = "";
			query += " delete board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 삭제되었습니다.");
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
		
		
		
	}
	
	
	//게시판열기
	public BoardVo getBoard(int no) {
		BoardVo getBoard = null;
		getConnection();
		
		
		try {
			//sql , 바인딩, 실행
			String query = "";
			query += " select b.no ";
			query += "         ,b.title ";
			query += "         ,b.content ";
			query += "         ,b.hit ";
			query += "         ,to_char(b.reg_date, 'YY-MM-DD HH24:MI') reg_date ";
			query += "         ,b.user_no ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			System.out.println(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				
				getBoard = new BoardVo(no, title, content, hit, regDate, userNo, name);
			}
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return getBoard;
	}
	
	
	//조회수 올리기
	public int hitup(int no) {
		int count = 0;
		getConnection();
		
		try {
			//sql문 // 바인딩 // 실행
			String query = "";
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 조회되었습니다.");
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
		
		
	}
	

	//수정
	public int update(BoardVo boardVo) {
		int count = 0;
		getConnection();
		
		try {
			//쿼리, 바인딩, 실행
			String query = "";
			query += " update board ";
			query += " set title = ? ";
			query += " ,content = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 수정되었습니다.");
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
		
		
	}
	
	
	//사용자 정보 가져오기 (게시물 작성)
	public UserVo getUsername(int no) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			//SQL준비
			String query = "";
			query += " select  name ";
			query += " from users ";
			query += " where no = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				String name = rs.getString("name");
				
				userVo = new UserVo();
				userVo.setName(name);
				
				
			}
			
			//.out.println("Dao.userVo : " + userVo);
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return userVo;
		
	}

	
	//게시물 입력
	public int insert(BoardVo boardVo) {
		int count = 0;
		getConnection();
		
		try {
			//쿼리문 / 바인딩 / 실행
			String query = " insert into board ";
			query += " values (seq_board_no.nextval, ";
			query += "     ?, ";
			query += "     ?, ";
			query += "     0, ";
			query += "     sysdate, ";
			query += "     ? ";
			query += " ) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 등록되었습니다.");
			
		}catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		return count;
		
	}
	




}
