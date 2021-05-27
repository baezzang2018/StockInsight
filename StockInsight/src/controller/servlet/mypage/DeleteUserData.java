package controller.servlet.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import model.bean.DAO.MyPageDAO;

/**
 * Servlet implementation class DeleteUserData
 */
@WebServlet("/deleteUserData")
public class DeleteUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserData() {
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
        
	    System.out.print(user_id);
	    int delete = MyPageDAO.deleteUserData(conn, user_id);
	    
	    if(delete != 1) {
	    	out.println("<script>alert(\"네트워크가 불안정해서 회원탈퇴가 실패하였습니다.\\n잠시 후 다시 시도해주세요. \"); location.href='jsp/main.jsp';</script>");
            out.flush();
	    }
	    else {
	        session.invalidate();
	    	out.println("<script>alert(\"회원탈퇴가 완료되었습니다.\"); location.href='jsp/main.jsp';</script>");
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
