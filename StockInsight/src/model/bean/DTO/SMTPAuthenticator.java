package model.bean.DTO;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class SMTPAuthenticator extends Authenticator{
	
	   protected PasswordAuthentication getPasswordAuthentication() {
	      final String username = "baezzang2018@gmail.com"; // gmail �����;
	      final String password = "baebaezzang!"; // �н�����;
	      return new PasswordAuthentication(username, password);
	   }

	}