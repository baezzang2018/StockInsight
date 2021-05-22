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
import model.bean.DAO.MyPageDAO;

/**
 * Servlet implementation class DoMyPage
 */
@WebServlet("/doMyPage")
public class DoMyPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoMyPage() {
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
        
        
//        HttpSession session = request.getSession();
//        String user_id = (String)session.getAttribute("ID");
        
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("DBconnection");
        
        String user_id = "jimin";

        ResultSet rs = MyPageDAO.checkMypage(conn, user_id); //user_id에 맞는 pwd 찾기
     
        if(rs != null) {
           try
           {
              if(rs.next()) { // existing user

            	  
            	  String psw = rs.getString(1);
//            	  String name = rs.getString(2);
//                  String user_mid = rs.getString(3);
//                  String email = rs.getString(4);
//                  String user_passwd = rs.getString(5);
//                  request.setAttribute("name", name);
//                  request.setAttribute("user_mid", user_mid);
//                  request.setAttribute("email", email);
//                  request.setAttribute("user_passwd", user_passwd); //사용자의 pw
                  
                  System.out.print(user_id+"님의 비밀번호는 "+psw+"입니다");
                 
                 
//                 RequestDispatcher view = request.getRequestDispatcher("mypage_edit.jsp");
//				  view.forward(request, response);
                 
                 
              }
           } catch (SQLException e) { 
              e.printStackTrace();
           } 
        } 
     
	    
//	    
	    RequestDispatcher view = request.getRequestDispatcher("/jsp/my_page/myPage.jsp");
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
