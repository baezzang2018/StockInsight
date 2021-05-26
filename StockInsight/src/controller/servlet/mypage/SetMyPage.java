package controller.servlet.mypage;

import java.io.IOException;
import java.io.PrintWriter;

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
import model.bean.DTO.LoginDTO;

/**
 * Servlet implementation class SetMyPage
 */
@WebServlet("/setMyPage")
public class SetMyPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetMyPage() {
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
		String user_id = (String) session.getAttribute("ID");// 아이디로 세션 받기
		// 세션 없으면 로그인으로 돌아가게 해주자!

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");
		LoginDTO mypage = LoginDAO.getUserListFromUserId(conn, user_id);

		String name = mypage.getUser_name();
		String id = mypage.getUser_id();
		String email = mypage.getUser_email();
		String pwd = mypage.getUser_pwd();

		request.setAttribute("name",name);
		request.setAttribute("id", id);
		request.setAttribute("email", email);
		request.setAttribute("pwd", pwd);

		RequestDispatcher view = request.getRequestDispatcher("/jsp/my_page/setMyPage.jsp");
		view.forward(request, response);	    
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
