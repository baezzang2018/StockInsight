package controller.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import model.bean.DAO.LoginDAO;
import model.bean.DAO.MyPageDAO;
import model.bean.DTO.LoginDTO;

/**
 * Servlet implementation class DoLogin
 */
@WebServlet("/searchLogin")
public class SearchLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchLogin() {
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

		HttpSession session = request.getSession();          
		PrintWriter out = response.getWriter();
		
		String user_id = request.getParameter("user_id");
		System.out.println("user_id"+user_id);
		String user_pwd = request.getParameter("user_pwd");
		String user_index = null;

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		LoginDTO login = LoginDAO.getUserListFromUserId(conn, user_id);

		int check_Index = login.getUser_index();
		System.out.println("index:" + check_Index);
		String check_id = login.getUser_id();
		System.out.println("id:" + check_id);
		String check_pwd = login.getUser_pwd();
	//	String check = "0"; // 로그인 실패시 가는 jsp에서 얼러트 제어 스트링, default : 0 -> 비밀번호 틀림

		if(check_id == null) {
			//check = "1";
			out.println("<script language='javascript'>");
			out.println("alert('가입된 정보가 없습니다.');");
			out.println("document.location.href=\"/StockInsight/searchLogin\" ;");				
			out.println("</script>");
			out.flush();
			
		/*	request.setAttribute("check", check);
			RequestDispatcher view = request.getRequestDispatcher("jsp/log_in/failureLogin.jsp");
			view.forward(request, response);*/
		}
		else {
			if(check_Index != 0) {
				user_index = Integer.toString(check_Index);			
				//세션 설정					
				session.setAttribute("INDEX",user_index); //index	
			}

			if(check_id.equals(user_id) && check_pwd.equals(user_pwd)) { // id, 비밀번호 동일
				
				session.setAttribute("ID",user_id);   //id 
				RequestDispatcher view = request.getRequestDispatcher("jsp/main.jsp");
				view.forward(request, response);	
			}		
			else {			
			//	request.setAttribute("check", check);				
				out.println("<script language='javascript'>");
				out.println("alert('비밀번호가 틀렸습니다.');");
				out.println("document.location.href=\"/StockInsight/searchLogin\" ;");				
				out.println("</script>");
				out.flush();
				
			//	RequestDispatcher view = request.getRequestDispatcher("jsp/log_in/failureLogin.jsp");
			//	view.forward(request, response);
			}
		}

		ResultSet okay = MyPageDAO.checkMypageinner(conn, user_id);
		if(okay != null){		
			try
			{
				if(okay.next()) { // existing user
					String name = okay.getString(2);  
					request.setAttribute("name",name);	
					session.setAttribute("NAME",name);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}

		/*
		//index 찾기
		ResultSet index_set = LoginDAO.findIndex(conn, user_id);


		if(index_set != null) {
			try
			{
				if(index_set.next()) { // existing user
					user_index = index_set.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}   
		}
		session.setAttribute("ID",user_id);   //id 
		session.setAttribute("INDEX",user_index); //index

		//      String str = (String) session.getAttribute("ID");
		//      String str2 = (String) session.getAttribute("INDEX");

		//ID
		ResultSet rs = LoginDAO.findUser(conn, user_id);  //id
		ResultSet okay = MyPageDAO.checkMypageinner(conn, user_id); //

		String check = null; // check 
		boolean go_okay = false; //go_okay 

		if(rs != null) {
			try
			{
				if(rs.next()) { // existing user
					String checkpw = rs.getString(1);
					if(checkpw.equals(user_pwd)){
						// valid user and passwd
						request.setAttribute("id",user_id);	
						go_okay= true;
					}
					else
					{
						check = "0";
					}
				}
				else { 
					check = "1";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}

		if(okay != null){		
     		try
			{
				if(okay.next()) { // existing user
					String name = okay.getString(2);  
					request.setAttribute("name",name);	
					session.setAttribute("NAME",name);
					go_okay= true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
     	}
		if(check != null) {
			request.setAttribute("check",check);
			RequestDispatcher view = request.getRequestDispatcher("jsp/log_in/login.jsp");
			view.forward(request, response);
		}
		if(go_okay == true) {
			RequestDispatcher view = request.getRequestDispatcher("jsp/main.jsp");
			view.forward(request, response);
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
