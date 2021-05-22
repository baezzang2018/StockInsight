package controller.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import model.bean.DAO.LoginDAO;


/**
 * Servlet implementation class DoFindPwd
 */
@WebServlet("/searchFindPwd")
public class SearchFindPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFindPwd() {
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

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		ResultSet rs = LoginDAO.findPWD(conn, name, id, email);
		String checkpwd = null;
		
		if(rs != null) {
			try
			{
				if(rs.next()) { // existing user
					checkpwd = rs.getString(1);	
					out.println("<script language='javascript'>");
					out.println("alert(\"" + name + "님의 비밀번호는 " + checkpwd + "입니다.\");");
					out.println("document.location.href=\"/StockInsight/searchLogin\" ;");				
					out.println("</script>");
					out.flush();
				}
				else {
					out.println("<script language='javascript'>");
					out.println("alert('가입된 정보가 없습니다.');");
					out.println("document.location.href=\"/StockInsight/searchLogin\" ;");				
					out.println("</script>");
					out.flush();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		/*if(checkpwd != null) {
			request.setAttribute("name", name);
			request.setAttribute("checkpwd", checkpwd);
			RequestDispatcher view = request.getRequestDispatcher("pwdfind.jsp");
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
