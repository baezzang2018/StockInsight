package controller.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import model.bean.DAO.LoginDAO;


/**
 * Servlet implementation class DoJoin
 */
@WebServlet("/saveJoin")
public class SaveJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveJoin() {
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
		
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("user_name");
		String id = request.getParameter("user_id");	
		String email = request.getParameter("user_email");	
		String pwd = request.getParameter("user_pwd");

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		boolean overlap = LoginDAO.checkID(conn, id);
		if (overlap == true) { // 아이디 중복
			out.println("<script language='javascript'>");
			out.println("alert('아이디 중복 되었습니다.');");
			out.println("document.location.href=\"/StockInsight/jsp/log_in/join.jsp\" ;");	
			out.println("</script>");
			out.flush();
		/*	request.setAttribute("name", name);
			request.setAttribute("id",id);
			request.setAttribute("pwd",pwd);
			request.setAttribute("email",email);
			RequestDispatcher view = request.getRequestDispatcher("jsp/log_in/overlapJoinId.jsp");
			view.forward(request, response); */
		}
		else { 
			int addCheck;
			try {
				addCheck = LoginDAO.addMember(conn, name, id, email, pwd);
				if(addCheck == 1) {
					out.println("<script language='javascript'>");				
					out.println("alert('회원가입이 완료되었습니다.');");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
					out.println("</script>");
					out.flush();
					//response.sendRedirect("jsp/log_in/login.jsp"); 
				} 
				else {
					out.println("<script language='javascript'>");				
					out.println("alert('회원가입이 실패하였습니다. 다시 한번 시도해주세요.');");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
					out.println("</script>");
					out.flush();
				}  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				         
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
