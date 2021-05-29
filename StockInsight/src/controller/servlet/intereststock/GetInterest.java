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
import model.bean.DTO.LoginDTO;
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
	      
	      try {
	              
		     LoginDTO login = LoginDAO.getUserListFromUserId(conn, user_id);
	      	 String user_index = Integer.toString(login.getUser_index());
      
	         ResultSet rs_stock_index = StockDAO.selectInterest(conn, user_index);
	         ArrayList<String> findStockIndexFromUser = new ArrayList<String>(); 

	         if (rs_stock_index != null) {
	            while (rs_stock_index.next()) {
	               String findStockIndex_FromUser = rs_stock_index.getString(1);
	               
	               findStockIndexFromUser.add(findStockIndex_FromUser);
	               request.setAttribute("findStockIndexFromUser", findStockIndexFromUser); 
	            }
	            
	         }
	                           
	         ArrayList<String> findStockFieldFromStockIndex = new ArrayList<String>();    
	         ArrayList<String> findCompanyList = new ArrayList<String>();   
	         ArrayList<String> findBeforeList = new ArrayList<String>(); 
	         ArrayList<String> findFutureList = new ArrayList<String>();
	         
	         if (findStockIndexFromUser != null) {
	        	 for (int i = 0; i < findStockIndexFromUser.size(); i++) { //stock_index list   
          	  
					StockDTO stock_list_from_index = StockDAO.getStockListFromStockIndex(conn, findStockIndexFromUser.get(i));
					
					findStockFieldFromStockIndex.add(stock_list_from_index.getStock_field());
					findCompanyList.add(stock_list_from_index.getStock_company());
					findBeforeList.add(Integer.toString(stock_list_from_index.getStock_before()));
					findFutureList.add(Integer.toString(stock_list_from_index.getStock_future()));
							
					request.setAttribute("findStockFieldFromStockIndex", findStockFieldFromStockIndex); 	
	       		    request.setAttribute("searchCompanyList", findCompanyList);   		    
	       		    request.setAttribute("findBeforeList", findBeforeList);  		    
	       		    request.setAttribute("findFutureList", findFutureList);   

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
