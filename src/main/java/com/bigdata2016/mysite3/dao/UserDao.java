package com.bigdata2016.mysite3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bigdata2016.mysite3.vo.UserVo;

@Repository
public class UserDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. connection 얻기
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			conn = DriverManager.getConnection(url, "webdev", "webdev");
			
		} catch (ClassNotFoundException ex) {
			System.out.println("JDBC 드라이버를 찾을 수 없습니다." + ex);
		}

		return conn;
	}
	
	public void insert(UserVo vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. Statement 준비
			String sql = 
				" insert" + 
				"   into member" + 
				" values( member_no_seq.nextval, ?, ?, ?, ?)";
					
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			// 5. query 실행
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("sql error:" + ex);
		} finally {
			// 6. 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("sql error:" + ex);
			}
		}
	}

	public UserVo get(Long no) {
		return null;
	}

	public UserVo get(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = 
					" select no, name, email" +
					"   from member" +
					"  where email = ?" +
					"    and password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email2 = rs.getString(3);
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email2);
			}
		} catch (SQLException ex) {
			System.out.println("sql error:" + ex);
		} finally {
			// 6. 자원 정리
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
			} catch (SQLException ex) {
				System.out.println("sql error:" + ex);
			}
		}

		return userVo;
	}
}
