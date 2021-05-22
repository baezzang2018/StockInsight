package controller.servlet.searchstock;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.DAO.StockDAO;

/**
 * Servlet implementation class GetStockCategory
 */
@WebServlet("/getStockCategory")
public class GetStockCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");
		PrintWriter out = response.getWriter();

		try {
			
			Statement st = conn.createStatement();
			String field = request.getParameter("field");
			request.setAttribute("field", field);
			
			ResultSet rs = StockDAO.findCompany(conn, field); // �꽑�깮 遺꾩빞�뿉 �뵲瑜� �쉶�궗�뱾 李얘린
			ResultSet rsf = StockDAO.findFieldSet(conn); //紐⑤뱺 遺꾩빞 由ы꽩 - 156媛� 
			
			ArrayList<String> searchCompanyList = new ArrayList<String>();
			ArrayList<String> searchFieldList = new ArrayList<String>();

			if (rs != null) {
				while (rs.next()) {
					String searchCompany = rs.getString(1); 
					searchCompanyList.add(searchCompany);
					request.setAttribute("searchCompanyList", searchCompanyList);
				}
			}
			
			if(rsf != null) {
				while(rsf.next()) {
					String searchField = rsf.getString(1);
					searchFieldList.add(searchField);
					request.setAttribute("searchFieldList", searchFieldList);
				}
			}
			
			RequestDispatcher view = sc.getRequestDispatcher("/jsp/search_stock/stockCompany.jsp");
			view.forward(request, response);
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
