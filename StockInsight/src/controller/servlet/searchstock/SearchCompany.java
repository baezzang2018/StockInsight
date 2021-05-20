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
					// String search = request.getParameter("search");
					String search;
					if (request.getParameter("search") == "") {
						search = null;
					} else {
						search = request.getParameter("search");
						
					}
					
					
					String btnUpDown = null; // �궡由쇱닚 �삤由꾩닚 踰꾪듉 諛쏆븘�삤湲� 
					
					if (request.getParameter("btnUpDown") == "") {
						btnUpDown = null;
					} else {
						btnUpDown = request.getParameter("btnUpDown");
						
					}
					

					if (search != null) {// 鍮꾩뼱�엳吏� �븡�� �엯�젰�씪 寃쎌슦
						request.setAttribute("search", search);

					  if(btnUpDown == null) { //寃��깋寃곌낵�뿉�꽌 �삤由꾩닚 踰꾪듉 �늻瑜댁� �븡怨� 寃��깋�빐�꽌 �뱾�뼱媛� 寃쎌슦 
						
						ResultSet rs = StockDAO.sortVolumeHigh(conn, search); // 寃��깋寃곌낵 鍮꾧탳
						ArrayList<String> searchList = new ArrayList<String>();

						if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
							System.out.print("not null");
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
								//RequestDispatcher view = sc.getRequestDispatcher("/search_after.jsp");
								//view.forward(request, response);
							}
							else {
								
								 String searchResult = "寃��깋 寃곌낵媛� �뾾�뒿�땲�떎."; searchList.add(searchResult);
								 request.setAttribute("searchResult", searchList);
								 request.setAttribute("count", count); System.out.println("searchResult: " +searchList); 
								
								 
								System.out.print("empty");
							}
							
							//RequestDispatcher view = sc.getRequestDispatcher("/search_after.jsp");
							//view.forward(request, response);
						} 
					  } else { // 寃��깋 寃곌낵�뿉�꽌 �삤由꾩닚 踰꾪듉 �닃�윭�꽌 �굹�삤�뒗 寃곌낵 
						//String btnVolumeLow, btnVolumeHigh ,btnBeforeLow , btnBeforeHigh, btnName;
						  //System.out.print("踰꾪듉�쓣 �닃���뼱�슂!!!");
						 // btnUpDown = request.getParameter("btnUpDown");
						  //System.out.print("�엫�엫�씪" + btnUpDown);
						  
						  if(btnUpDown.equals("btnVolumeLow")) {
						
							  ResultSet rs = StockDAO.findSearchCompany(conn, search);	
				               ArrayList<String> searchList = new ArrayList<String>();
				               
				               if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
				            	   System.out.print("not null");
				            	   
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
				               
				               if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
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
				               
				               if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
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
				               
				               if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
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
				               
				               if (rs != null) {// 諛쏆븘�삩 DB由ъ뒪�듃媛� null�씠 �븘�땺 寃쎌슦
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
					  
					  
					} else { // 鍮덉엯�젰�씪 寃쎌슦 寃쎄퀬李�
						out.println("<script>alert('寃��깋�뼱瑜� �엯�젰�빐二쇱꽭�슂.'); history.go(-1);</script>");
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
