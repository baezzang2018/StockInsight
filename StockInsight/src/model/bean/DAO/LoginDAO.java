package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.DTO.LoginDTO;

public class LoginDAO {	
	
	public static LoginDTO getUserListFromUserId(Connection con, String selectId){ // 로그인 user_index에 해당하는 모든 것 리턴 
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      //ArrayList<LoginDTO> list = new ArrayList<LoginDTO>();
	      LoginDTO user = new LoginDTO();
	      try {
	    	  //LoginDTO user = new LoginDTO();
	    	  
	         StringBuffer query = new StringBuffer();
	         query.append("SELECT * FROM Stockinsight.User WHERE user_id =?");
	         pstmt = con.prepareStatement(query.toString());
	         pstmt.setString(1, selectId);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	//LoginDTO user = new LoginDTO();
	        	user.setUser_index(rs.getInt("user_index"));
	        	user.setUser_name(rs.getString("user_name"));
	        	user.setUser_id(rs.getString("user_id"));
	        	user.setUser_email(rs.getString("user_email"));
	        	user.setUser_pwd(rs.getString("user_pwd"));
	        	user.setUser_admin(rs.getInt("user_admin"));
	        	
	            //list.add(user);
	         }
	      } catch (SQLException e) {

	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return user;
	   }
	/*
	public static ResultSet findIndex(Connection con, String mid) {
		String sql = "SELECT user_index FROM User WHERE user_id=";
		Statement st;

		try {
			st = con.createStatement();

			if (st.execute(sql + "'" + mid + "'")) {
				return st.getResultSet();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet findUser(Connection con, String mid) {

		String sqlSt = "SELECT user_pwd FROM User WHERE user_id=";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlSt + "'" + mid + "'")) {
				return st.getResultSet();
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	*/
	
	public static ResultSet findID(Connection conn, String input_name, String input_email) {

		String sqlSt = "SELECT user_id FROM User WHERE user_name=";
		Statement st;
		try {

			st = conn.createStatement();

			if (st.execute(sqlSt + "'" + input_name + "'and user_email='" + input_email + "'")) {
				return st.getResultSet();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Boolean checkID(Connection conn, String input_id) {

		String idfind_Sql = "SELECT user_id FROM User WHERE user_id=";

		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(idfind_Sql+ "'" + input_id + "'");

			while (rs.next()) {
				String id = rs.getString(1);
				if(id.equals(input_id)) {
					return true;
				}
				else {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static ResultSet findPWD(Connection conn, String input_name, String input_id, String input_email) {

		String sqlSt = "SELECT user_pwd FROM User WHERE user_name=";
		Statement st;
		try {

			st = conn.createStatement();

			if (st.execute(sqlSt + "'" + input_name + "'and user_id = '" + input_id + "'and user_email='" + input_email + "'")) {
				return st.getResultSet();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}  
	
	public static void addMember(Connection con, String new_name, String new_id,
			String new_email, String new_pwd) throws SQLException {

		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("INSERT INTO User (user_name, user_id, user_email, user_pwd) VALUES(?, ?,? ," + "SHA2(?,512))");
			pstmt.setString(1, new_name);
			pstmt.setString(2, new_id);
			pstmt.setString(3, new_email);
			pstmt.setString(4, new_pwd);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {pstmt.close();}

		}

	}
	
	public static String getUserIdByIndex(Connection conn, String idx) {
		Statement stmt = null;
		String questionQuery = "SELECT user_id FROM User WHERE user_index="+idx;
		String user_id = "";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				user_id = rs.getString(1);
			}
			return user_id;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet findUserIndex(Connection con, String user_id) {

		String sqlinter = "SELECT user_index From Stockinsight.User WHERE user_id ="; // �쉶�궗紐낆뿉 �씪移섑븯�뒗 遺꾩빞 由ы꽩 
		Statement st;
		try {

			st = con.createStatement();
			System.out.println("user_id:" + user_id);
			if (st.execute(sqlinter + "'" + user_id + "'")) {
				return st.getResultSet(); // field �꽆源� 

			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
}
