package controller.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * Servlet implementation class SearchFindPwd
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
        final String from = "baezzang2018@gmail.com";              

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
                   int random = (int)(Math.random() * (999999 - 100000 + 1)) + 100000; // six random
                       String random_pwd = Integer.toString(random);

                       final String to = email;  

                       String subject = "StockInsight 임시 비밀번호 입니다.";// title
                       String content = "안녕하세요. StockkInsight입니다.\n"+ name +" 님의 임시 비밀번호는 " + random_pwd + "입니다.\n\n해당 임시비밀번호로 로그인 후 마이페이지에서 비밀번호를 변경 해주시기 바랍니다.\n감사합니다.";// content

                      
                 if (from.trim().equals("")) {
                    System.out.println("보내는 사람이 없습니다.");
                 }
                 else if (to.trim().equals("")) {
                    System.out.println("받는 사람이 없습니다.");
                 } 
                 else {
                    try {
                       Mailsystem mt = new Mailsystem();
   
                          // 이메일 보내기
                             mt.sendEmail(from, to, subject, content);                     

                             // user 디비 비번 업데이트
                             LoginDAO.updatePWD(conn, id, random_pwd);

                        out.println("<script language='javascript'>");
                             out.println("alert('이메일로 임시 비밀번호를 보냈습니다.\\n확인부탁드립니다.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                             
                          } catch (MessagingException me) {
                             out.println("<script language='javascript'>");
                             out.println("alert('네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                          } catch (Exception e) {
                             out.println("<script language='javascript'>");
                             out.println("alert('가입된 이메일이 유효하지 않습니다.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                          }
                 }

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