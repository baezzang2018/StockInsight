package model.bean.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RankingDAO {
	public static String[] getRanking(Connection con, String standard) {
		// standard 기준으로 상위 10개 반환

		String[] companyName = new String[10];

		//SELECT stock_company FROM Stock ORDER BY stock_volume DESC, stock_before
		String sql = "SELECT stock_company FROM Stock ORDER BY "+ standard +" DESC, stock_before"; 
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				// 상위 10개만 companyName에 추가
				if(count<=9) {
					companyName[count] = rs.getString(1);
					count++;
				}
				else {
					break;
				}
			}
			// 상위 10개 반환
			return companyName;
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// 실패 시
		return null;
	}
	
	// 상위권 5개 받아오는 코드
		public static String[][] getTopFive(Connection conn) {
			String[][] result = new String[5][3];
			String sql = "select stock_code, stock_company, ((stock_future)-(stock_before))/stock_before*100 from Stock order by ((stock_future)-(stock_before))/stock_before*100 DESC";
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				int count = 0;
				while (rs.next()) {
					// 상위 5개만 companyName에 추가
					if(count<=4) {
						result[count][0] = rs.getString(1);
						result[count][1] = rs.getString(2);
						result[count][2] = rs.getString(3);
						count++;
					}
					else {
						break;
					}
				}
				// 상위 10개 반환
				return result;
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// 실패 시
			return null;
		}

		// 하위권 5개 받아오는 코드
		public static String[][] getDownFive(Connection conn) {
			String[][] result = new String[5][3];
			String sql = "select stock_code, stock_company, ((stock_future)-(stock_before))/stock_before*100 from Stock order by ((stock_future)-(stock_before))/stock_before*100";
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				int count = 0;
				while (rs.next()) {
					// 하위 5개만 companyName에 추가
					if(rs.getString(3).equals("-100.0000"))
						continue;
					if (count <= 4) {
						result[count][0] = rs.getString(1);
						result[count][1] = rs.getString(2);
						result[count][2] = rs.getString(3);
						count++;
					} else {
						break;
					}
				}
				// 상위 10개 반환
				return result;
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// 실패 시
			return null;

		}
}
