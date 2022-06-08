package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

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
			query += " select no ";
			query += "         ,title ";
			query += "         ,content ";
			query += "         ,hit ";
			query += "         ,to_char(reg_date, 'YY-MM-DD HH24:MI') reg_date ";
			query += "         ,user_no ";
			query += " from board ";
			query += " order by reg_date asc ";
			
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				
				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo);
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
			query += " select no ,title ,content ,hit ,to_char(reg_date, 'YY-MM-DD HH24:MI') reg_date ,user_no ";
			query += " from board ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				
				getBoard = new BoardVo(no, title, content, hit, regDate, userNo);
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
		
}
