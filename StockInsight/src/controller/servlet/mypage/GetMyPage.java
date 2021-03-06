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
 * Servlet implementation class GetMyPage
 */
@WebServlet("/getMyPage")
public class GetMyPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyPage() {
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
        
        
        HttpSession session = request.getSession();
	    String user_id = (String) session.getAttribute("ID");// 아이디로 세션 받기
	    // 세션 없으면 로그인으로 돌아가게 해주자!

        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("DBconnection");
        
        String encrypted_pwd= "";
		String user_pwd = request.getParameter("user_pwd"); //jsp에서 넘겨준 비밀번호 입력 데이터
        
		LoginDTO mypage = LoginDAO.getUserListFromUserId(conn, user_id);
        ResultSet rs = MyPageDAO.encryptedPwd(conn, user_pwd); // 입력 비밀번호 암호
        

        try {
        	if(rs.next()) {
        		encrypted_pwd = rs.getString(1);
        	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(!mypage.getUser_pwd().equals(encrypted_pwd)) { // 비밀번호 입력이 불일치
        	out.println("<script>alert(\"올바른 비밀번호가 아닙니다. 다시 입력해주세요.\"); location.href='jsp/my_page/myPage.jsp';</script>");
            out.flush();
        }
        else { // 입력한 비밀번호와 일치
        	
        	String name = mypage.getUser_name();
			String id = mypage.getUser_id();
        	String email = mypage.getUser_email();
        	String pwd = mypage.getUser_pwd();
        	
        	request.setAttribute("name",name);
        	request.setAttribute("id", id);
        	request.setAttribute("email", email);
        	request.setAttribute("pwd", pwd);
        	
        	RequestDispatcher view = request.getRequestDispatcher("/jsp/my_page/getMyPage.jsp");
			view.forward(request, response);
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
