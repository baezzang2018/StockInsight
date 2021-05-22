package controller.servlet.qna;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class WriteQuestion
 */
@WebServlet("/writeQuestion")
public class WriteQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteQuestion() {
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
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(cal.getTime());

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		// 로그인 되어 있는 상태인지 체크
		HttpSession session = request.getSession(false);
		String checkIndex = (String)session.getAttribute("INDEX");
		
		if (session == null || checkIndex == null) {
			// 로그인 안 되어있으면 login.html 반환
			response.sendRedirect("login.jsp");
			// 혹시 모르니까 한번 더 무효화
			session.invalidate();
		} else {
			// 로그인 되어 있으면 (1)question db update, (2)작성된 글 보는 페이지 반환
			// 사용자 idx와 name 가져오기
			int uidx =  Integer.parseInt((String)session.getAttribute("INDEX"));
			String name = (String)session.getAttribute("ID");
			
			String questionIndex = QnaDAO.getAutoIncrement(conn, "Question");
			int number = QnaDAO.addQuestion(conn, uidx, title, content, date);
			Boolean admin = QnaDAO.checkAdmin(conn, uidx);
			if (number!=-1) {
				// 작성한 post 넘기기
				QnaDTO post = QnaDAO.getQuestionByQuestionIndex(conn, questionIndex);
				request.setAttribute("post",post);
				request.setAttribute("admin",admin);
				RequestDispatcher view = request.getRequestDispatcher("jsp/qna/qnaContent.jsp");
				view.forward(request, response);
			}else {
				// 문의글 작성 실패 시
				PrintWriter out = response.getWriter();
				out.println("<script language='javascript'>");
	            out.println("alert('문의글 등록에 실패했습니다.');");
	            out.println("document.location.href=\"/StockInsight/writeQuestion\" ;");            
	            out.println("</script>");
	            out.flush();
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
