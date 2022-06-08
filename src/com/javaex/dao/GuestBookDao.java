package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;

public class GuestBookDao {
	
	
	//필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//메소드일반
	
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
	public List<GuestBookVo> getGuestBookList(){
		List<GuestBookVo> guestbookList = new ArrayList<GuestBookVo>();
		getConnection();
	
		try {
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no ";
			query += "         ,name ";
			query += "         ,password ";
			query += "         ,content ";
			query += "         ,reg_date ";
			query += " from guestbook ";
			System.out.println(query);
			
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			
			
		    // 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestBookVo gestbookVo = new GuestBookVo(no, name, pw, content, regDate);
				guestbookList.add(gestbookVo);
			}
			

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return guestbookList;
	}
	
	public int insert(GuestBookVo guestbookVo) {
		int count = 0;
		getConnection();
		
		try {
			//SQL,바인딩,실행
			String query = "";
			query += " insert into guestbook ";
			query += " values (seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getcontent());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 등록되었습니다.");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		

		close();
		return count;
	}

	//삭제
	public int delete(GuestBookVo guestbookVo) {
		int count = 0;
		getConnection();
		
		try {
			//sql, 바인딩, 실행
			String query = "";
			query += " delete from guestbook ";
			query += " where no= ? ";
			query += " and password= ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, guestbookVo.getNo());
			pstmt.setString(2, guestbookVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 삭제되었습니다.");
			
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		
		
		close();
		return count;
		
	}
	
	
		
		
	
	
	
}
