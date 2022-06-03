package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
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
	
	//회원가입 -- > 정보 입력
	public int insert(UserVo userVo) {
		int count = 0;
		
		getConnection();
		
		try {
			//SQL문 준비
			String query ="";
			query += " insert into users ";
			query += " values (seq_users_no.nextval, ?, ?, ?, ?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			//실행
			count = pstmt.executeUpdate();
			
			//실행결과
			System.out.println(count + "건 저장되었습니다.");
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
		
	}
	
	//사용자 정보 가져오기 (로그인 시 사용)
	public UserVo getUser(UserVo userVo) {
		UserVo authUser = null;
		
		getConnection();
		
		try {
			//SQL준비
			String query = "";
			query += " select no, id, password, name, gender ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				authUser = new UserVo();
				authUser.setNo(no);
				authUser.setId(id);
				authUser.setPassword(password);
				authUser.setName(name);
				authUser.setGender(gender);
				
				
			}
			
			
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return authUser;
		
	}
	
	
	//회원정보 수정
	public int update(UserVo userVo) {
		int count = 0;
		getConnection();
		
		try {
			//SQL준비
			String query = "";
			query += " update users ";
			query += " set name = ? ";
			query += "     ,password = ? ";
			query += "     ,gender = ? ";
			query += " where no = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			System.out.println(query);
			System.out.println(userVo.toString());
			//실행
			count = pstmt.executeUpdate(query);
			
			//결과처리
			System.out.println(count + "건 수정되었습니다.");
			
			
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
	}
	
	
	
	
	
}
