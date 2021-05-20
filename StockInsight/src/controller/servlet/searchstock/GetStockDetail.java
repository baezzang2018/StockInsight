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
import javax.servlet.http.HttpSession;

import model.bean.DAO.StockDAO;
import model.bean.DTO.StockDTO;
import model.bean.DAO.LoginDAO;

/**
 * Servlet implementation class GetStockDetail
 */
@WebServlet("/getStockDetail")
public class GetStockDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockDetail() {
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
				
				HttpSession session = request.getSession();
				String user_id = (String) session.getAttribute("ID");
				String user_index = null;
				String stock_index = null;

				int value = 0;
				
				String selectCompany = request.getParameter("selectCompany");
				request.setAttribute("selectCompany", selectCompany); 
				
				StockDTO stock_list_from_company = StockDAO.getStockListFromStockCompany(conn, selectCompany);
				StockDTO stock_list_from_index = StockDAO.getStockListFromStockIndex(conn, stock_index);
				
				try {	
					Statement st = conn.createStatement();
					
					 // user_index 
					ResultSet rs_user_id = LoginDAO.findUserIndex(conn, user_id);
			         
			         
			         if (rs_user_id != null) {
				            while (rs_user_id.next()) {
				               user_index = rs_user_id.getString(1);
				               request.setAttribute("user_index", user_index); 
				            }
				         }
			         
			         System.out.print("doSearchFinal_user_index : " + user_index + "\n");	         
					
					
			        request.setAttribute("stock_index", stock_list_from_company.getStock_index()); 
					request.setAttribute("selectFuture", Integer.toString(stock_list_from_index.getStock_future())); 
		              
		            //interest_index 
		            Boolean interCheck = StockDAO.interestCheck(conn, user_index, stock_index);
		            request.setAttribute("interCheck", interCheck);                 
					
					request.setAttribute("selectField", stock_list_from_company.getStock_field());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				request.setAttribute("stock_code", stock_list_from_index.getStock_code());
				value = 2;
						
				
				if(value !=0) {
					RequestDispatcher view =  request.getRequestDispatcher("/jsp/search_stock/stockDetail.jsp");
					view.forward(request, response);
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
