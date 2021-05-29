package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.DTO.StockDTO;

public class StockDAO {
	public static ResultSet findCompany(Connection con, String field) { // 선택한 분야의 회사 찾는 함수 

		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_field =";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + field + "'")) {
				return st.getResultSet();
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet findSearchCompany(Connection con, String search) { // 검색문자가 들어간 회사 종류 찾는 함수 

		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'")) { // 해당 문자열이 들어간 결과 반환  
				return st.getResultSet();
			}


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet findFieldSet(Connection con) {

		String sqlinter = "SELECT DISTINCT stock_field FROM Stockinsight.Stock"; // 모든 분야 리턴 
		Statement st;
		try {

			st = con.createStatement();
			if (st.execute(sqlinter)) {
				return st.getResultSet(); // field 넘김 

			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	static int interest_index = 0;
	public static void insertInterest(Connection con, String user_index, String stock_index ) {

		PreparedStatement pstmt = null;
		try {  	
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("INSERT INTO Stockinsight.Interest (interest_index, User_user_index, Stock_stock_index) VALUES(?, ?, ?)"); // 관심 종목 삽입 
			pstmt.setInt(1, interest_index);
			pstmt.setString(2, user_index);
			pstmt.setString(3, stock_index);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
	}


	public static ResultSet deleteInterest(Connection con, String user_index, String stock_index ) {

		String sqlinter = "DELETE FROM Stockinsight.Interest WHERE User_user_index="; // 관심 종목에 들어가있는 항목 삭제 
		String sqlinter2 = "and Stock_stock_index=";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + user_index + "'" + sqlinter2 + "'" + stock_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field 넘김 
			}



		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet selectInterest(Connection con, String user_index) {

		String sqlinter = "SELECT Stock_stock_index FROM Stockinsight.Interest WHERE User_user_index ="; // 유저로부터 stock_index 얻어내기 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + user_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field 넘김 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet divStockIndexByField(Connection con, String stock_field) { // interest table 안의 stock_index를 분야별로 나누기 

		String sqlstock = "SELECT Stock_stock_index FROM Stockinsight.Interest WHERE Stock_stock_index IN (SELECT stock_index FROM Stockinsight.Stock WHERE Stockinsight.Stock.stock_field ="; //분야에 해당하는 stock_index 가져오기 

		Statement st;

		try {

			st = con.createStatement();

			if (st.execute(sqlstock + "'" + stock_field + "')" )) {

				return st.getResultSet(); 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static Boolean interestCheck(Connection con, String user_index ,String stock_index) { // interest table 안의 stock_index를 분야별로 나누기 

		String sql = "SELECT interest_index FROM Stockinsight.Interest WHERE User_user_index ="; 
		String sqltwo = "AND Stock_stock_index =";

		Statement st;

		try {

			st = con.createStatement(); 

			if (st.execute(sql + "'" + user_index + "'" + sqltwo + "'" + stock_index + "'" )) {
				if(st.getResultSet().next()) {
					return true;
				}
				else {
					return false;
				}

			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet sortVolumeLow(Connection con, String search) {
		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		String sql = "ORDER BY stock_volume ASC";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'" + sql)) { // 해당 문자열이 들어간 결과 반환 
				return st.getResultSet();
			}


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet sortVolumeHigh(Connection con, String search) {
		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		String sql = "ORDER BY stock_volume DESC";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'" + sql)) { // 해당 문자열이 들어간 결과 반환 
				return st.getResultSet();
			}


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet sortBeforeLow(Connection con, String search) {
		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		String sql = "ORDER BY stock_before ASC";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'" + sql)) { // 해당 문자열이 들어간 결과 반환 
				return st.getResultSet();
			}
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet sortBeforeHigh(Connection con, String search) {
		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		String sql = "ORDER BY stock_before DESC";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'" + sql)) { // 해당 문자열이 들어간 결과 반환 
				return st.getResultSet();
			}


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet sortName(Connection con, String search) {
		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		String sql = "ORDER BY stock_company ASC";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'" + sql)) { // 해당 문자열이 들어간 결과 반환 
				return st.getResultSet();
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static Boolean rmInterest(Connection conn, String user_index) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("DELETE from Interest where User_user_index = ?");
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
	
	public static StockDTO getStockListFromStockCompany(Connection con, String selectCompany){ // 회사명에 일치하는 분야 리턴 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockDTO stock = new StockDTO();
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM Stockinsight.Stock WHERE stock_company =?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, selectCompany);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				stock.setRealtime_data(rs.getString("realtime_data"));
				stock.setStock_before(rs.getInt("stock_before"));
				stock.setStock_code(rs.getString("stock_code"));
				stock.setStock_company(rs.getString("stock_company"));
				stock.setStock_field(rs.getString("stock_field"));
				stock.setStock_future(rs.getInt("stock_future"));
				stock.setStock_index(rs.getInt("stock_index"));
				stock.setStock_volume(rs.getInt("stock_volume"));
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return stock;
	}
	
	public static StockDTO getStockListFromStockIndex(Connection con, String selectIndex){ // 회사명에 일치하는 분야 리턴 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockDTO stock = new StockDTO();
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM Stockinsight.Stock WHERE stock_Index =?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, selectIndex);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				stock.setRealtime_data(rs.getString("realtime_data"));
				stock.setStock_before(rs.getInt("stock_before"));
				stock.setStock_code(rs.getString("stock_code"));
				stock.setStock_company(rs.getString("stock_company"));
				stock.setStock_field(rs.getString("stock_field"));
				stock.setStock_future(rs.getInt("stock_future"));
				stock.setStock_index(rs.getInt("stock_index"));
				stock.setStock_volume(rs.getInt("stock_volume"));
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return stock;
	}
}
