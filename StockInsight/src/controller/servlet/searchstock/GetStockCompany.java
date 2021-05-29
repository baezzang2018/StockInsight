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
 * Servlet implementation class GetStockCompany
 */
@WebServlet("/getStockCompany")
public class GetStockCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockCompany() {
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
		 /*      ResultSet rs = StockDAO.findFieldSet(conn);
		              ArrayList<String> searchFieldList = new ArrayList<String>();
	
		         if (rs != null) {
		            while (rs.next()) {
		               String searchField = rs.getString(1);
		               searchFieldList.add(searchField);
		               request.setAttribute("searchFieldList", searchFieldList);
		            }
		         }
		*/         
	            
		        String field = request.getParameter("field");
	            
	            if(field == null) {
	            	field = "1차 비철금속 제조업"; //default
	            }else {
	            	field = request.getParameter("field"); //선택 분야 
	            }
	            
				request.setAttribute("field", field);
				
				ResultSet rsc = StockDAO.findCompany(conn, field); 
				ResultSet rsf = StockDAO.findFieldSet(conn); 
				
				ArrayList<String> searchCompanyList = new ArrayList<String>();
				ArrayList<String> searchFieldList = new ArrayList<String>();

				if (rsc != null) {
					while (rsc.next()) {
						String searchCompany = rsc.getString(1); 
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
