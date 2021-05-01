package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockDAO {
	public static ResultSet findCompany(Connection con, String field) { // ������ �о��� ȸ�� ã�� �Լ� 

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

	public static ResultSet findSearchCompany(Connection con, String search) { // �˻����ڰ� �� ȸ�� ���� ã�� �Լ� 

		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_company LIKE '%";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter +  search + "%'")) { // �ش� ���ڿ��� �� ��� ��ȯ 
				return st.getResultSet();
			}


		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		return null;
	}

	public static ResultSet findFieldSelectCompany(Connection con, String selectCompany) { // ȸ��� ��ġ�ϴ� �о� ���� --> �������� �˻� ��� �ѷ��� �� ��� 

		String sqlinter = "SELECT stock_field FROM Stockinsight.Stock WHERE stock_company ="; // ȸ��� ��ġ�ϴ� �о� ���� 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + selectCompany + "'")) {
				return st.getResultSet(); // field �ѱ� 

			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet findFieldSet(Connection con) {

		String sqlinter = "SELECT DISTINCT stock_field FROM Stockinsight.Stock"; // ��� �о� ���� 
		Statement st;
		try {

			st = con.createStatement();
			if (st.execute(sqlinter)) {
				return st.getResultSet(); // field �ѱ� 

			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet findStockIndex(Connection con, String selectCompany) {

		String sqlinter = "SELECT stock_index FROM Stockinsight.Stock WHERE stock_company ="; // Stock ���̺��� stock_index �������� 
		Statement st;
		try {

			st = con.createStatement();
			//System.out.println("DBUtil_findStockIndex_selectCompany:" + selectCompany);
			if (st.execute(sqlinter + "'" + selectCompany + "'")) {
				return st.getResultSet(); // field �ѱ� 

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
			//System.out.print("  interest_index: " + interest_index);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("INSERT INTO Stockinsight.Interest (interest_index, User_user_index, Stock_stock_index) VALUES(?, ?, ?)"); // ���� ���� ���� 
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

		String sqlinter = "DELETE FROM Stockinsight.Interest WHERE User_user_index="; // ���� ���� ���ִ� �׸� ���� 
		String sqlinter2 = "and Stock_stock_index=";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + user_index + "'" + sqlinter2 + "'" + stock_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}



		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	
	public static ResultSet findStockIndexFromUser(Connection con, String user_index) {

		String sqlinter = "SELECT Stock_stock_index FROM Stockinsight.Interest WHERE User_user_index ="; // �����κ��� stock_index ���� 
		String sqlfield = "";
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + user_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet findStockFieldFromStockIndex(Connection con, String stock_index) {

		String sqlinter = "SELECT stock_field FROM Stockinsight.Stock WHERE stock_index = "; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + stock_index + "'" )) {

				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet findStockCompanyFromStockIndex(Connection con, String stock_company) { //stock_index�� company �̸� ���ϱ� 

		String sqlinter = "SELECT stock_company FROM Stockinsight.Stock WHERE stock_index ="; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + stock_company + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet findStockBeforeFromStockIndex(Connection con, String stock_index) { //stock_index�� company �̸� ���ϱ� 

		String sqlinter = "SELECT stock_before FROM Stockinsight.Stock WHERE stock_index ="; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + stock_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}


	public static ResultSet findStockFutureFromStockIndex(Connection con, String stock_index) { //stock_index�� company �̸� ���ϱ� 

		String sqlinter = "SELECT stock_future FROM Stockinsight.Stock WHERE stock_index ="; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sqlinter + "'" + stock_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}


	public static ResultSet divStockIndexByField(Connection con, String stock_field) { // interest table ���� stock_index�� �оߺ��� ������ 

		String sqlstock = "SELECT Stock_stock_index FROM Stockinsight.Interest WHERE Stock_stock_index IN (SELECT stock_index FROM Stockinsight.Stock WHERE Stockinsight.Stock.stock_field ="; //�о߿� �ش��ϴ� stock_index �������� 

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

	public static Boolean interestCheck(Connection con, String user_index ,String stock_index) { // interest table ���� stock_index�� �оߺ��� ������ 

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
	
	public static ResultSet find_stockcode(Connection con, String stock_index) { //stock_index�� company �̸� ���ϱ� 
		System.out.println("DBU: " + stock_index);
		String sql = "SELECT stock_code FROM Stockinsight.Stock WHERE stock_index ="; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sql + "'" + stock_index + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	} 
	
	public static ResultSet getStockCode(Connection con, String companyName) {
		String sql = "SELECT stock_code FROM Stockinsight.Stock WHERE stock_company ="; 
		Statement st;
		try {

			st = con.createStatement();

			if (st.execute(sql + "'" + companyName + "'" )) {
				//interest_index = - 1;
				return st.getResultSet(); // field �ѱ� 
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

			if (st.execute(sqlinter +  search + "%'" + sql)) { // �ش� ���ڿ��� �� ��� ��ȯ 
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

			if (st.execute(sqlinter +  search + "%'" + sql)) { // �ش� ���ڿ��� �� ��� ��ȯ 
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

			if (st.execute(sqlinter +  search + "%'" + sql)) { // �ش� ���ڿ��� �� ��� ��ȯ 
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

			if (st.execute(sqlinter +  search + "%'" + sql)) { // �ش� ���ڿ��� �� ��� ��ȯ 
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

			if (st.execute(sqlinter +  search + "%'" + sql)) { // �ش� ���ڿ��� �� ��� ��ȯ 
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
}
