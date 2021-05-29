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
		
		String input_user_id = request.getParameter("user_id");
		String input_user_pwd = request.getParameter("user_pwd");
		
		String user_index = null; //session index
		String user_name = null; //session name
		
		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		//login id, pwd compare
		ResultSet login_rs = LoginDAO.compareLogin(conn, input_user_id, input_user_pwd);
		
		if(login_rs != null) {
			try
			{
				if(login_rs.next()) { // existing user
					user_index = login_rs.getString(1);
					user_name = login_rs.getString(2); 
					
					session.setAttribute("INDEX",user_index); //index session
					request.setAttribute("name",user_name);	
					session.setAttribute("NAME",user_name); //name session
					session.setAttribute("ID",input_user_id);   //user correct! 
					
					out.println("<script language='javascript'>");
					out.println("alert('반갑습니다.\\n스톡인사이트와 함께 당신의 주식투자에 성공하세요!');");
					out.println("document.location.href=\"/StockInsight/jsp/main.jsp\" ;");				
					out.println("</script>");
					out.flush();
				}
				else {
					out.println("<script language='javascript'>");
					out.println("alert('등록된 정보가 없거나 비밀번호가 틀렸습니다.');");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
					out.println("</script>");
					out.flush();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}   
		}
		else {
			out.println("<script language='javascript'>");
			out.println("alert('등록된 정보가 없거나 비밀번호가 틀렸습니다.');");
			out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
			out.println("</script>");
			out.flush();
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
