package controller.servlet.intereststock;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.DAO.LoginDAO;
import model.bean.DAO.StockDAO;
import model.bean.DTO.StockDTO;

/**
 * Servlet implementation class DeleteInterest
 */
@WebServlet("/deleteInterest")
public class DeleteInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteInterest() {
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
		   
		            String del_user_index = request.getParameter("user_index");
		            request.setAttribute("user_index", del_user_index);
		            
		            String del_stock_index = request.getParameter("stock_index"); 
		            request.setAttribute("stock_index", del_stock_index);   
		            
		            String selectField = request.getParameter("selectField");
		            request.setAttribute("selectField", selectField);
		            
		            String selectCompany = request.getParameter("selectCompany");
		            request.setAttribute("selectCompany", selectCompany);
		            
		            Boolean interCheck = StockDAO.interestCheck(conn, del_user_index, del_stock_index); //愿��떖 醫낅ぉ�뿉 �엳�뒗吏� �뾾�뒗吏� 泥댄겕 
		            
		            if(interCheck == true) { //愿��떖醫낅ぉ�뿉 �엳�쑝硫� 
		            	StockDAO.deleteInterest(conn, del_user_index, del_stock_index);// user_id濡� user_index 李얘린 
		            	interCheck = false;
		            }else {
		            	interCheck = false;
		            }
		            
		            StockDTO stock_list_from_company = StockDAO.getStockListFromStockCompany(conn, selectCompany);
					StockDTO stock_list_from_index = StockDAO.getStockListFromStockIndex(conn, del_stock_index);
		            
					request.setAttribute("stock_code", stock_list_from_company.getStock_code());
					
					String st_stock_index = null;
			        st_stock_index = Integer.toString(stock_list_from_company.getStock_index());

			        request.setAttribute("selectFuture", stock_list_from_index.getStock_future()); 
			        
		            request.setAttribute("interCheck", interCheck);
		            RequestDispatcher view = sc.getRequestDispatcher("/jsp/search_stock/stockDetail.jsp");
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
