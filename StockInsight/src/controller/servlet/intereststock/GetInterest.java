package controller.servlet.intereststock;

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

import model.bean.DAO.LoginDAO;
import model.bean.DAO.StockDAO;
import model.bean.DTO.StockDTO;


/**
 * Servlet implementation class GetInterest
 */
@WebServlet("/getInterest")
public class GetInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInterest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		  request.setCharacterEncoding("UTF-8");
	      response.setContentType("text/html; charset=UTF-8");
	      
	      ServletContext sc = getServletContext();
	      Connection conn = (Connection) sc.getAttribute("DBconnection");
	      PrintWriter out = response.getWriter();
	     
	      HttpSession session = request.getSession();
	      String user_id = (String) session.getAttribute("ID");
	      String user_index = null;
   
	      try {
	         
	         Statement st = conn.createStatement();
	         
	         ResultSet rs_user_id = LoginDAO.findUserIndex(conn, user_id);
	          
	         if (rs_user_id != null) {
		            while (rs_user_id.next()) {
		               user_index = rs_user_id.getString(1);
		               request.setAttribute("user_index", user_index); 
		            }
		         }
      
	         ResultSet rs_stock_index = StockDAO.findStockIndexFromUser(conn, user_index);
	         ArrayList<String> findStockIndexFromUser = new ArrayList<String>(); 

	         if (rs_stock_index != null) {
	            while (rs_stock_index.next()) {
	               String findStockIndex_FromUser = rs_stock_index.getString(1);
	               
	               findStockIndexFromUser.add(findStockIndex_FromUser);
	               request.setAttribute("findStockIndexFromUser", findStockIndexFromUser); 
	            }
	            
	         }
	                 
	         ResultSet rs_stock_field = null;
	         ArrayList<String> findStockIndex_FromUser = (ArrayList<String>) request.getAttribute("findStockIndexFromUser"); // user媛   꽔   醫낅ぉ index 由ъ뒪 듃 
	         ArrayList<String> findStockFieldFromStockIndex = new ArrayList<String>(); // 씤 뜳 뒪濡쒕  꽣 遺꾩빞 異붿텧 
	         
	         ResultSet rs_stock_company = null;
	         ArrayList<String> findCompanyList = new ArrayList<String>();
	         
	         ResultSet rs_stock_before = null;
	         ArrayList<String> findBeforeList = new ArrayList<String>();
	         
	         ResultSet rs_stock_future = null;
	         ArrayList<String> findFutureList = new ArrayList<String>();
	         
	         if (findStockIndex_FromUser != null) {
	        	 for (int i = 0; i < findStockIndex_FromUser.size(); i++) { //stock_index list 뿉 꽌 stock_index  븯 굹 뵫 媛  졇   꽌 field 援ы븯湲   
          	  
					StockDTO stock_list_from_index = StockDAO.getStockListFromStockIndex(conn, findStockIndex_FromUser.get(i));
					
					request.setAttribute("findStockFieldFromStockIndex", stock_list_from_index.getStock_field()); 	                     
	       		    request.setAttribute("searchCompanyList", stock_list_from_index.getStock_company());
	       		    request.setAttribute("findBeforeList", stock_list_from_index.getStock_before()); 
	       		    request.setAttribute("findFutureList", stock_list_from_index.getStock_future());   

	           }
		         }
			      RequestDispatcher view = sc.getRequestDispatcher("/jsp/interest_stock/interestStock.jsp");
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
