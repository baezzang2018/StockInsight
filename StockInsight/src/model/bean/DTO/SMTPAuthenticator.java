package model.bean.DTO;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class SMTPAuthenticator extends Authenticator{
	
	   protected PasswordAuthentication getPasswordAuthentication() {
	      final String username = "baezzang2018@gmail.com"; // gmail 사용자;
	      final String password = "baebaezzang!"; // 패스워드;
	      return new PasswordAuthentication(username, password);
	   }

	}