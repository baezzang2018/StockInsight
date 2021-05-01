package model.bean.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RankingDAO {
	public static String[] getRanking(Connection con, String standard) {
		// standard �������� ���� 10�� ��ȯ

		String[] companyName = new String[10];

		//SELECT stock_company FROM Stock ORDER BY stock_volume DESC, stock_before
		String sql = "SELECT stock_company FROM Stock ORDER BY "+ standard +" DESC, stock_before"; 
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				// ���� 10���� companyName�� �߰�
				if(count<=9) {
					companyName[count] = rs.getString(1);
					count++;
				}
				else {
					break;
				}
			}
			// ���� 10�� ��ȯ
			return companyName;
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// ���� ��
		return null;
	}
	
	// ������ 5�� �޾ƿ��� �ڵ�
		public static String[][] getTopFive(Connection conn) {
			String[][] result = new String[5][3];
			String sql = "select stock_code, stock_company, ((stock_future)-(stock_before))/stock_before*100 from Stock order by ((stock_future)-(stock_before))/stock_before*100 DESC";
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				int count = 0;
				while (rs.next()) {
					// ���� 5���� companyName�� �߰�
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
				// ���� 10�� ��ȯ
				return result;
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// ���� ��
			return null;
		}

		// ������ 5�� �޾ƿ��� �ڵ�
		public static String[][] getDownFive(Connection conn) {
			String[][] result = new String[5][3];
			String sql = "select stock_code, stock_company, ((stock_future)-(stock_before))/stock_before*100 from Stock order by ((stock_future)-(stock_before))/stock_before*100";
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				int count = 0;
				while (rs.next()) {
					// ���� 5���� companyName�� �߰�
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
				// ���� 10�� ��ȯ
				return result;
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// ���� ��
			return null;

		}
}
