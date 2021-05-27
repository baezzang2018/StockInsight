package controller.servlet.mypage;

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
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import model.bean.DAO.LoginDAO;
import model.bean.DAO.MyPageDAO;
import model.bean.DTO.LoginDTO;

/**
 * Servlet implementation class SetUserData
 */
@WebServlet("/setUserData")
public class SetUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetUserData() {
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
	    String email = request.getParameter("email"); 
		String pwd = request.getParameter("pwd");
		// 세션 없으면 로그인으로 돌아가게 해주자!

        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("DBconnection");
		
		MyPageDAO.setUserData(conn,email,pwd,user_id);
		LoginDTO mypage = LoginDAO.getUserListFromUserId(conn, user_id);
		String name = mypage.getUser_name();
		request.setAttribute("name",name);
		request.setAttribute("id", user_id);
		request.setAttribute("email", email);
		request.setAttribute("pwd", pwd);
		
		RequestDispatcher view = request.getRequestDispatcher("/jsp/my_page/getMyPage.jsp");
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
