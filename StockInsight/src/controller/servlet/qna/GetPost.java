package controller.servlet.qna;

import java.io.IOException;
import java.sql.Connection;

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
 * Servlet implementation class GetPost
 */
@WebServlet("/getPost")
public class GetPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// type, index
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		// question, answer
		String type = request.getParameter("type");
		// 글의 index
		String index = request.getParameter("index");
		
		QnaDTO post;
		if (type.equals("question")) {
			post = QnaDAO.getQuestionByQuestionIndex(conn, index);
		} else {
			post = QnaDAO.getAnswerByAnswerIndex(conn, index);
		}

		// 세션 확인
		// 로그인 되어 있는 상태인지 체크
		Boolean admin = false;
		HttpSession session = request.getSession(false);
		String check_index = (String) session.getAttribute("INDEX");
		System.out.println(session);
		if (session == null || check_index == null) {
			System.out.println("세션없음");
			admin = false;
			// 혹시 모르니까 한번 더 무효화
			session.invalidate();
		} else {
			System.out.println("세션있음");
			if (type.equals("answer")) {
				admin = false;
			} else {
				int uidx = Integer.parseInt(check_index);
				admin = QnaDAO.checkAdmin(conn, uidx);
			}
		}

		request.setAttribute("post", post);
		request.setAttribute("admin", admin);
		RequestDispatcher view = request.getRequestDispatcher("jsp/qna/qnaContent.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
