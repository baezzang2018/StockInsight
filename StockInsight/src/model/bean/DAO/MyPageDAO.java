package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyPageDAO {
	// 입력 비밀번호 값 암호
	public static ResultSet encryptedPwd(Connection con, String input_pwd) {
		Statement stmt = null;
		String sqlSt = "SELECT user_pwd FROM Stockinsight.User WHERE user_pwd = SHA2('" + input_pwd + "',512)";
		try {
			stmt = con.createStatement();
			if (stmt.execute(sqlSt)) {
				return stmt.getResultSet();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean setUserData(Connection con, String input_email, String input_pwd, String mid) {
		Statement stmt = null;
		String sqlSt = "UPDATE User SET user_email ='" + input_email+"', user_pwd = SHA2('" + input_pwd + "',512) WHERE user_id='"+mid+"'";
		try {
			stmt = con.createStatement();
			if (stmt.execute(sqlSt)) {
			System.out.print("업뎃성공 ");
				return true;
			}
			else return false;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static int deleteUserData(Connection con, String mid) {
		Statement stmt = null;
		String sqlSt = "DELETE FROM Stockinsight.User WHERE user_id = '"+mid+"'";
		try {	
			stmt = con.createStatement();
			if(stmt.execute(sqlSt)) {
				System.out.print("삭제성공 ");
				
				return 1;
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block

			System.out.print("삭제실패");
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	
	

	
	
	
	
	
	
	
	public static ResultSet checkMypage(Connection con, String mid) {

		String sqlSt = "SELECT user_pwd FROM User WHERE user_id=";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlSt + "'" + mid + "'")) {
				System.out.println("1");
				return st.getResultSet();
			}

		} catch (SQLException e) {
			System.out.println("22222222222222222222222222222222");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet checkMypageinner(Connection con, String mid) {

		String sqlSt = "SELECT * FROM User WHERE user_id=";
		Statement st;
		try {

			st = con.createStatement();
			String str = (sqlSt + "'" + mid + "'");

			if (st.execute(str)) {
				//System.out.println("1");
				System.out.println("여기왔어?");
				return st.executeQuery(str);
			}

		} catch (SQLException e) {
			System.out.println("2");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet checkMypageedit(Connection con, String mid, String new_email, String new_passwd) {

		String sqlSt = "UPDATE User SET user_email ='" + new_email+"', user_pwd='" +new_passwd+"' WHERE user_id='"+mid+"'";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlSt)) {
				//System.out.println("1");
				System.out.println("수정하러왔어요");
				return st.executeQuery(sqlSt);
			}

		} catch (SQLException e) {
			System.out.println("수정실패");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static Boolean rmUser(Connection conn, String user_index) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("DELETE from User where user_index = ?");
			pstmt.setString(1, user_index);
			pstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {pstmt.close();}
		}
		return null;
	}

}
