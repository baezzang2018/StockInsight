package model.bean.DTO;

public class QnaDTO {
	private Boolean isQuestion;
	private String index;
	private String writer; //작성자 id
	private String title;
	private String content;
	private String date;
	private String questionIndex;

	public QnaDTO() {
		isQuestion = true;
		index = "";
		writer = "";
		title = "";
		content = "";
		date = "";
		questionIndex = "";
	}

	QnaDTO(Boolean isQuestion, String index, String writer, String title, String content, String date) {
		this.isQuestion = isQuestion;
		this.index = index;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.date = date;
		questionIndex = "";
	}

	public void setQnaDTO(Boolean isQuestion, String index, String writer, String title, String content, String date) {
		this.isQuestion = isQuestion;
		this.index = index;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.date = date;
		questionIndex = "";
	}
	
	//답변글인 경우, 질문글의 index
	public void setQuestionIndex(String index) {
		this.questionIndex = index;
	}

	public String getQuestionIndex() {
		return questionIndex;
	}
	
	public String printQuestionIndex() {
		if(this.isQuestion) { //질문글인 경우
			return index;
		}
		else { //답변글인 경우, 질문글의 index
			return ">[re]"+questionIndex;
		}
	}
	
	//질문글 index or 답변글 index
	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex() {
		return index;
	}

	public Boolean getIsQuestion() {
		return isQuestion;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return index + " " + writer + " " + title + " " + content + " " + date;
	}
}
