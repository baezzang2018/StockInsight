package model.bean.DTO;

public class LoginDTO {
	int user_index;
	String user_name;
	String user_id;
	String user_email;
	String user_pwd;
	int user_admin;
		 
	public LoginDTO() {
		super();
		user_index = 0;
		user_name = null;
		user_id = null;
		user_email = null;
		user_pwd = null;
		user_admin = 0;
		// TODO Auto-generated constructor stub
	}

	public LoginDTO(int user_index, String user_name, String user_id, String user_email, String user_pwd,
			int user_admin) {
		super();
		this.user_index = user_index;
		this.user_name = user_name;
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_pwd = user_pwd;
		this.user_admin = user_admin;
	}
	
	public int getUser_index() {
		return user_index;
	}
	public void setUser_index(int user_index) {
		this.user_index = user_index;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public int getUser_admin() {
		return user_admin;
	}
	public void setUser_admin(int user_admin) {
		this.user_admin = user_admin;
	}
	
	
	
}
