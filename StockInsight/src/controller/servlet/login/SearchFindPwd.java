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

                       String subject = "StockInsight ?????? ???????????? ?????????.";// title
                       String content = "???????????????. StockkInsight?????????.\n"+ name +" ?????? ?????? ??????????????? " + random_pwd + "?????????.\n\n?????? ????????????????????? ????????? ??? ????????????????????? ??????????????? ?????? ???????????? ????????????.\n???????????????.";// content

                      
                 if (from.trim().equals("")) {
                    System.out.println("????????? ????????? ????????????.");
                 }
                 else if (to.trim().equals("")) {
                    System.out.println("?????? ????????? ????????????.");
                 } 
                 else {
                    try {
                       Mailsystem mt = new Mailsystem();
   
                          // ????????? ?????????
                             mt.sendEmail(from, to, subject, content);                     

                             // user ?????? ?????? ????????????
                             LoginDAO.updatePWD(conn, id, random_pwd);

                        out.println("<script language='javascript'>");
                             out.println("alert('???????????? ?????? ??????????????? ???????????????.\\n????????????????????????.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                             
                          } catch (MessagingException me) {
                             out.println("<script language='javascript'>");
                             out.println("alert('??????????????? ??????????????????. ?????? ??? ?????? ??????????????????.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                          } catch (Exception e) {
                             out.println("<script language='javascript'>");
                             out.println("alert('????????? ???????????? ???????????? ????????????.');");
                             out.println("document.location.href=\"/StockInsight/jsp/log_in/login.jsp\" ;");            
                             out.println("</script>");
                             out.flush();
                          }
                 }

              }
              else {
                 out.println("<script language='javascript'>");
                 out.println("alert('????????? ????????? ????????????.');");
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