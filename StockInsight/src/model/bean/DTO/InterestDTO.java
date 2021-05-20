package model.bean.DTO;

public class InterestDTO {
	
	private	int interest_index;
	private	int User_user_index;
	private	int Stock_stock_index;
	
	public InterestDTO(int interest_index, int user_user_index, int stock_stock_index) {
		super();
		this.interest_index = interest_index;
		User_user_index = user_user_index;
		Stock_stock_index = stock_stock_index;
	}
	
	public int getInterest_index() {
		return interest_index;
	}
	public void setInterest_index(int interest_index) {
		this.interest_index = interest_index;
	}
	public int getUser_user_index() {
		return User_user_index;
	}
	public void setUser_user_index(int user_user_index) {
		User_user_index = user_user_index;
	}
	public int getStock_stock_index() {
		return Stock_stock_index;
	}
	public void setStock_stock_index(int stock_stock_index) {
		Stock_stock_index = stock_stock_index;
	}
	
	
}
