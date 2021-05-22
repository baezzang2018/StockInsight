package model.bean.DTO;

public class StockDTO {
	private	int stock_index;
	private String stock_company;
	private String realtime_data;
	private int stock_volume;
	private String stock_field;
	private int stock_before;
	private int stock_future;
	private String stock_code;
	
	public StockDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockDTO(int stock_index, String stock_company, String realtime_data, int stock_volume, String stock_field,
			int stock_before, int stock_future, String stock_code) {
		super();
		this.stock_index = stock_index;
		this.stock_company = stock_company;
		this.realtime_data = realtime_data;
		this.stock_volume = stock_volume;
		this.stock_field = stock_field;
		this.stock_before = stock_before;
		this.stock_future = stock_future;
		this.stock_code = stock_code;
	}
	
	public int getStock_index() {
		return stock_index;
	}
	public void setStock_index(int stock_index) {
		this.stock_index = stock_index;
	}
	public String getStock_company() {
		return stock_company;
	}
	public void setStock_company(String stock_company) {
		this.stock_company = stock_company;
	}
	public String getRealtime_data() {
		return realtime_data;
	}
	public void setRealtime_data(String realtime_data) {
		this.realtime_data = realtime_data;
	}
	public int getStock_volume() {
		return stock_volume;
	}
	public void setStock_volume(int stock_volume) {
		this.stock_volume = stock_volume;
	}
	public String getStock_field() {
		return stock_field;
	}
	public void setStock_field(String stock_field) {
		this.stock_field = stock_field;
	}
	public int getStock_before() {
		return stock_before;
	}
	public void setStock_before(int stock_before) {
		this.stock_before = stock_before;
	}
	public int getStock_future() {
		return stock_future;
	}
	public void setStock_future(int stock_future) {
		this.stock_future = stock_future;
	}
	public String getStock_code() {
		return stock_code;
	}
	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}
	
	
	
	
	
}
