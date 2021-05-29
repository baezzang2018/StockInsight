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
 * Servlet implementation class SearchCompany
 */
@WebServlet("/searchCompany")
public class SearchCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCompany() {
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

				int count = 0;

				ServletContext sc = getServletContext();
				Connection conn = (Connection) sc.getAttribute("DBconnection");
				PrintWriter out = response.getWriter();

				try {
					Statement st = conn.createStatement();
					
					String search;
					if (request.getParameter("search") == "") {
						search = null;
					} else {
						search = request.getParameter("search");
						
					}

					String btnUpDown = null; 
					
					if (request.getParameter("btnUpDown") == "") {
						btnUpDown = null;
					} else {
						btnUpDown = request.getParameter("btnUpDown");
						
					}

					if (search != null) {
						request.setAttribute("search", search);

					  if(btnUpDown == null) { 
						
						ResultSet rs = StockDAO.sortVolumeHigh(conn, search); 
						ArrayList<String> searchList = new ArrayList<String>();

						if (rs != null) {		
							if (rs.next()) {
								rs = StockDAO.sortVolumeHigh(conn, search);
								while (rs.next()) {
									count++;
									String searchResult = rs.getString(1);
									btnUpDown = "btnVolumeHigh";
									searchList.add(searchResult);
									request.setAttribute("click", btnUpDown);
									request.setAttribute("searchResult", searchList);
									request.setAttribute("count", count);
								}
							}
							else {
								
								 String searchResult = null; 
								 searchList.add(searchResult);
								 btnUpDown = "btnVolumeHigh";
								 request.setAttribute("click", btnUpDown);
								 request.setAttribute("searchResult", searchList);
								 request.setAttribute("count", count);
							}
						}else { // 검색어 없을 시, 
							
						}
					  } else {  
						  if(btnUpDown.equals("btnVolumeLow")) {
						
							  ResultSet rs = StockDAO.findSearchCompany(conn, search);	
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {    	   
				                     while (rs.next()) {
				                    	count++;
				                        String searchResult = rs.getString(1);
				                        
				                        searchList.add(searchResult);
				                        request.setAttribute("click", btnUpDown);
				                        request.setAttribute("searchResult", searchList);
				                        request.setAttribute("count", count);
				                     }
				               }               

							  
						  }else if (btnUpDown.equals("btnVolumeHigh")) {
							  ResultSet rs = StockDAO.sortVolumeHigh(conn, search);
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {
				                     while (rs.next()) {
				                        count++;
				                        String searchResult = rs.getString(1);
				                        
				                        searchList.add(searchResult);
				                        request.setAttribute("click", btnUpDown);
				                        request.setAttribute("searchResult", searchList);
				                        request.setAttribute("count", count);
				                     }
				               }      

							  
						  }else if (btnUpDown.equals("btnBeforeLow")) {
							  ResultSet rs = StockDAO.sortBeforeLow(conn, search);
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {
				                     while (rs.next()) {
				                        count++;
				                        String searchResult = rs.getString(1);
				                       
				                        searchList.add(searchResult);
				                        request.setAttribute("click", btnUpDown);
				                        request.setAttribute("searchResult", searchList);
				                        request.setAttribute("count", count);
				                     }
				               }      

							  
						  }else if (btnUpDown.equals("btnBeforeHigh")) {
							  
							  ResultSet rs = StockDAO.sortBeforeHigh(conn, search);
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {
				                     while (rs.next()) {
				                        count++;
				                        String searchResult = rs.getString(1);
				                       
				                        searchList.add(searchResult);
				                        request.setAttribute("click", btnUpDown);
				                        request.setAttribute("searchResult", searchList);
				                        request.setAttribute("count", count);
				                     }
				               }      

						  }else if (btnUpDown.equals("btnName")) { //btnName
							  
							  ResultSet rs = StockDAO.sortName(conn, search);
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {
				                     while (rs.next()) {
				                        count++;
				                        String searchResult = rs.getString(1);
				                       
				                        searchList.add(searchResult);
				                        request.setAttribute("click", btnUpDown);
				                        request.setAttribute("searchResult", searchList);
				                        request.setAttribute("count", count);
				                     }
				               }      

						  } 
					  }
					  
					  RequestDispatcher view = sc.getRequestDispatcher("/jsp/search_stock/searchCompany.jsp");
					  view.forward(request, response);
					  
					  
					} else { // 검색어를 입력 안했을 시, 
						out.println("<script>alert('검색어를 입력해주세요.'); history.go(-1);</script>");
						out.close();
					}
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
				response.setContentType("text/html; charset=UTF-8");

				doGet(request, response);

	}

}
