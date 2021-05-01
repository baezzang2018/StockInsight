package model.bean.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PopUpDAO {
	public static ResultSet popupplus(Connection con, String mid) {

		String sqlSt = 
				"select stock_company, stock_future-stock_before as Gap, stock_code\r\n" + 
						"from Stock\r\n" + 
						"where stock_future-stock_before>0 and stock_index=any(select Stock_stock_index\r\n" + 
						"from User inner join Interest on User.user_index=Interest.User_user_index \r\n" + 
						"where user_id = '"+mid+"') order by gap desc limit 1;";
		Statement st;
		try {

			st = con.createStatement();
			// String str = (sqlSt + "'" + mid + "'");

			if (st.execute(sqlSt)) {
				//System.out.println("1");
				// System.out.println("여기왔어?");
				// System.out.println("here");
				return st.executeQuery(sqlSt);
			}

		} catch (SQLException e) {
			//System.out.println("2");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public static ResultSet popupminus(Connection con, String mid) {

		String sqlSt = 
				"select stock_company, stock_future-stock_before as Gap, stock_code\r\n" + 
						"from Stock\r\n" + 
						"where stock_future-stock_before<0 and stock_index=any(select Stock_stock_index\r\n" + 
						"from User inner join Interest on User.user_index=Interest.User_user_index \r\n" + 
						"where user_id = '"+mid+"') order by gap limit 1;";
		Statement st;
		try {

			st = con.createStatement();
			// String str = (sqlSt + "'" + mid + "'");

			if (st.execute(sqlSt)) {
				//System.out.println("1");
				// System.out.println("여기왔어?");
				// System.out.println("here");
				return st.executeQuery(sqlSt);
			}

		} catch (SQLException e) {
			//System.out.println("2");
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

}
