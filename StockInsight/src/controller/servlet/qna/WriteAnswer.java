package controller.servlet.qna;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.DAO.QnaDAO;
import model.bean.DTO.QnaDTO;

/**
 * Servlet implementation class WriteAnswer
 */
@WebServlet("/writeAnswer")
public class WriteAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteAnswer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String questionIndex = request.getParameter("number");
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(cal.getTime());

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		// 로그인 되어 있는 상태인지 체크
		HttpSession session = request.getSession(false);
		if (session == null) {
			// 로그인 안 되어있으면 login반환
			response.sendRedirect("login.jsp");
		} else {
			int success = QnaDAO.addAnswer(conn, title, content, date, questionIndex);
			
			if (success!=-1) {
				// 제대로 삽입되었으면
				QnaDTO post = QnaDAO.getAnswerByQuestionIndex(conn, questionIndex);
				request.setAttribute("post",post);
				RequestDispatcher view = request.getRequestDispatcher("jsp/qna/qnaContent.jsp");
				view.forward(request, response);
			}else {
				// 다시 문의하기 페이지로
				response.sendRedirect("/getQnaList?pageIndex=1");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
