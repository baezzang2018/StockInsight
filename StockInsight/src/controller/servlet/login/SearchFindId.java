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
 * Servlet implementation class DofindId
 */
@WebServlet("/searchFindId")
public class SearchFindId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFindId() {
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
		String email = request.getParameter("user_email");	

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		ResultSet rs = LoginDAO.findID(conn, name, email);
		
		if(rs != null) {
			try
			{
				if(rs.next()) { // existing user
					String db_id = rs.getString(1);
					out.println("<script language='javascript'>");
					out.println("alert(\"" + name + "님의 아이디는 " + db_id + "입니다.\");");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
					out.println("</script>");
					out.flush();
				}
				else {
					out.println("<script language='javascript'>");
					out.println("alert('가입된 정보가 없습니다.');");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");			
					out.println("</script>");
					out.flush();
				}
			} catch (SQLException e) {
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
