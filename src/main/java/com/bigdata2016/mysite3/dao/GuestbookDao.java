package com.bigdata2016.mysite3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bigdata2016.mysite3.vo.GuestbookVo;

@Repository
public class GuestbookDao {

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

	public void delete( GuestbookVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. Statement 준비
			String sql = 
				" delete" +
				"   from guestbook" +
			    "  where no = ?" +
			    "    and password = ?";		
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

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
	
	public void insert(GuestbookVo vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. Statement 준비
			String sql = " insert" + "   into  guestbook" + " values  (GUESTBOOK_SEQ.nextval, ?, ?, ?, SYSDATE )";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

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

	public List<GuestbookVo> getList() {

		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 2. connection 얻기
			conn = getConnection();

			// 3. Statement 생성
			stmt = conn.createStatement();

			// 4. query 실행
			String sql = "   select no, name, message, to_char(reg_date,'YYYY-MM-DD HH:MI:SS')" + "     from guestbook"
					+ " order by reg_date desc";

			// 5. query 실행
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage(message);
				vo.setRegDate(regDate);

				result.add(vo);
			}
		} catch (SQLException ex) {
			System.out.println("sql error:" + ex);
		} finally {
			// 6. 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("sql error:" + ex);
			}
		}

		return result;
	}
}
