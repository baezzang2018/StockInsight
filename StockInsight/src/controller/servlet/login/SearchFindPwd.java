package controller.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import model.bean.DAO.LoginDAO;
import model.bean.DTO.Mailsystem;

/**
 * Servlet implementation class DoFindPwd
 */
@WebServlet("/searchFindPwd")
public class SearchFindPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchFindPwd() {
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

		//email
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String backupDate = sf.format(date);
		final String from = "baezzang2018@gmail.com"; // 메일 보내는 사람

		String name = request.getParameter("user_name");
		String id = request.getParameter("user_id");
		String email = request.getParameter("user_email");	

		ServletContext sc = getServletContext();
		Connection conn = (Connection) sc.getAttribute("DBconnection");

		ResultSet rs = LoginDAO.findPWD(conn, name, id, email);
		String checkpwd = null;

		if(rs != null) {
			try
			{
				if(rs.next()) { // existing user
					//임시비밀번호 생성
					int random = (int)(Math.random() * (999999 - 100000 + 1)) + 100000; // 6자리
					String random_pwd = Integer.toString(random);

					//이메일 비밀번호 보내기 코드 
					final String to = email;   // 메일 받는 사람

					String subject = backupDate+"StockInsight 임시 비밀번호입니다.";// 제목
					String content = "안녕하세요. 반갑습니다. StockkInsight입니다.\n"+ name +" 님의 임시비밀번호는 " + random_pwd + "입니다.\n해당 임시비밀번호로 로그인 후 마이페이지에서 비밀번호를 변경 해주시기 바랍니다. \n감사합니다.";// 내용

					if (from.trim().equals("")) {
						System.out.println("보내는 사람을 입력하지 않았습니다.");
					} else if (to.trim().equals("")) {
						System.out.println("받는 사람을 입력하지 않았습니다.");
					} else {
						try {

							Mailsystem mt = new Mailsystem();

							// 메일보내기
							mt.sendEmail(from, to, subject, content);							

							// 임시 비번 디비 업데이트
							LoginDAO.updatePWD(conn, id, random_pwd);

							System.out.println("메일 전송에 성공하였습니다.");
							out.println("<script language='javascript'>");
							out.println("alert(\"임시 비밀번호를 이메일로 전송했습니다. 확인 부탁드립니다.\");");
							out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
							out.println("</script>");
							out.flush();


						} catch (MessagingException me) {
							System.out.println("메일 전송에 실패하였습니다.");
							System.out.println("1.실패 이유 : " + me.getMessage());
							out.println("<script language='javascript'>");
							out.println("alert(\"오류 1번\");");
							out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
							out.println("</script>");
							out.flush();
						} catch (Exception e) {
							System.out.println("메일 전송에 실패하였습니다.");
							System.out.println("2.실패 이유 : " + e.getMessage());
							out.println("<script language='javascript'>");
							out.println("alert(\"오류 2번\");");
							out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
							out.println("</script>");
							out.flush();
						}
					}

					/*	checkpwd = rs.getString(1);	
					out.println("<script language='javascript'>");
					out.println("alert(\"" + name + "님의 비밀번호는 " + checkpwd + "입니다.\");");
					out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");				
					out.println("</script>");
					out.flush();*/
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
		/*if(checkpwd != null) {
			request.setAttribute("name", name);
			request.setAttribute("checkpwd", checkpwd);
			RequestDispatcher view = request.getRequestDispatcher("pwdfind.jsp");
			view.forward(request, response);
		}*/
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
