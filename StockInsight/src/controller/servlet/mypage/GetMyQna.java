package controller.servlet.mypage;

import java.io.IOException;
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

import model.bean.DAO.MyPageDAO;
import model.bean.DAO.QnaDAO;
import model.bean.DTO.QnaDTO;

/**
 * Servlet implementation class GetMyQna
 */
@WebServlet("/getMyQna")
public class GetMyQna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyQna() {
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
	    String check_index = (String) session.getAttribute("INDEX");
	      

	    
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("DBconnection");
		ArrayList<QnaDTO> postList = QnaDAO.getMyAllPost(conn, check_index);
        
		request.setAttribute("postList",postList);
        
		RequestDispatcher view = request.getRequestDispatcher("jsp/my_page/myQnaList.jsp");
		view.forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
