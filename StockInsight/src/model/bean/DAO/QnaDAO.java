package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import model.bean.DTO.QnaDTO;

public class QnaDAO {
	// 관리자 권한 체크
	public static Boolean checkAdmin(Connection conn, int uidx) {
		// admin == true
		String selectSql = "SELECT user_admin FROM User WHERE user_index=" + Integer.toString(uidx);

		Statement st;
		int number = -1;

		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectSql);

			System.out.println(rs.toString());
			while (rs.next()) {
				number = rs.getInt(1);
				if (number == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/* 조회 */
	// 모든 질문&답변글 가져오기
	public static ArrayList<QnaDTO> getAllPost(Connection conn) {
		ArrayList<QnaDTO> qnaList = new ArrayList<QnaDTO>(); // question and answer list
		HashMap<String, QnaDTO> answerList = getAllAnswer(conn); // questionIndex - answer

		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String writer = "";
		String writerName = "";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Question order by ques_index DESC";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				QnaDTO question = new QnaDTO();

				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				writer = LoginDAO.getUserIdByIndex(conn, rs.getString(5));
				writerName = LoginDAO.getUserNameByUserId(conn, writer);
				if (writer == null || writerName==null) {
					writer = ""; // 작성자가 탈퇴한 경우
					writerName = "";
				}
				question.setQnaDTO(true, index, writer, title, content, date);
				question.setWriterName(writerName);
				qnaList.add(question);

				QnaDTO answer = answerList.get(index);
				if (answer != null) {
					qnaList.add(answer);
				}

			}
			return qnaList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return qnaList;
	}
	
	public static ArrayList<QnaDTO> getMyAllPost(Connection conn, String user_index) {
		ArrayList<QnaDTO> qnaList = new ArrayList<QnaDTO>(); // question and answer list
		HashMap<String, QnaDTO> answerList = getAllAnswer(conn); // questionIndex - answer

		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String writer = "";

		Statement stmt = null;
//		String questionQuery = "SELECT * FROM Question order by ques_index DESC";

		String questionQuery = "SELECT * FROM Question WHERE User_user_index="+user_index+" order by ques_index DESC";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				QnaDTO question = new QnaDTO();

				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				writer = LoginDAO.getUserIdByIndex(conn, rs.getString(5));
				if (writer == null) {
					writer = ""; // 작성자가 탈퇴한 경우
				}
				question.setQnaDTO(true, index, writer, title, content, date);
				qnaList.add(question);

				QnaDTO answer = answerList.get(index);
				if (answer != null) {
					qnaList.add(answer);
				}

			}
			return qnaList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return qnaList;
	}
	
	
	
	
	

	// 모든 답변글(질문글index,답변글 형태) 가져오기
	public static HashMap<String, QnaDTO> getAllAnswer(Connection conn) {
		HashMap<String, QnaDTO> answerList = new HashMap<String, QnaDTO>();

		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String question_index = "";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Answer order by Question_ques_index DESC";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				QnaDTO answer = new QnaDTO();

				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				question_index = rs.getString(5);

				answer.setQnaDTO(false, index, "admin", title, content, date);
				answer.setWriterName("admin");
				answer.setQuestionIndex(question_index);

				answerList.put(question_index, answer);
			}
			return answerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return answerList;
	}

	// questionIndex로 Question 가져오기
	public static QnaDTO getQuestionByQuestionIndex(Connection conn, String idx) {

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Question WHERE ques_index=" + idx;

		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String writer = "";
		String writerName = "";

		try {
			QnaDTO question = new QnaDTO();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				// index, title, content, date, answer_index, user_index
				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				writer = LoginDAO.getUserIdByIndex(conn, rs.getString(5));
				writerName = LoginDAO.getUserNameByUserId(conn, writer);
				if (writer == null || writerName == null) {
					writer = ""; // 작성자가 탈퇴한 경우
					writerName = "";
				}
				question.setQnaDTO(true, index, writer, title, content, date);
				question.setWriterName(writerName);
			}
			return question;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 내(index)가 작성한 Question 가져오기
	public static ArrayList<QnaDTO> getMyQuestion(Connection conn, String userIndex) {
		Statement stmt = null;
		ArrayList<QnaDTO> myQuestionList = new ArrayList<QnaDTO>();
		String myQuestionQuery = "SELECT * FROM Question WHERE User_user_index=" + userIndex
				+ "ORDER BY ques_index DESC";

		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String writer = "";
		String writerName = "";

		try {
			QnaDTO question = new QnaDTO();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(myQuestionQuery);
			while (rs.next()) {
				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				writer = LoginDAO.getUserIdByIndex(conn, rs.getString(5));
				writerName = LoginDAO.getUserNameByUserId(conn, writer);
				if (writer == null || writerName == null) {
					writer = ""; // 작성자가 탈퇴한 경우
					writerName = "";
				}
				question.setQnaDTO(true, index, writer, title, content, date);
				question.setWriterName(writerName);
				myQuestionList.add(question);
			}
			return myQuestionList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return myQuestionList;
	}

	// answerIndex로 Answer 가져오기
	public static QnaDTO getAnswerByAnswerIndex(Connection conn, String idx) {
		QnaDTO answer = new QnaDTO();

		String title = "";
		String content = "";
		String date = "";
		String question_index = "";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Answer WHERE answer_index=" + idx;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				question_index = rs.getString(5);
			}
			answer.setQnaDTO(false, idx, "admin", title, content, date);
			answer.setWriterName("admin");
			answer.setQuestionIndex(question_index);
			return answer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// questionIndex로 answer 가져오기 (이전 이름 = getAnswerPostUseQuesIdx)
	public static QnaDTO getAnswerByQuestionIndex(Connection conn, String ques_idx) {
		QnaDTO answer = new QnaDTO();

		String index = "";
		String title = "";
		String content = "";
		String date = "";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Answer WHERE Question_ques_index=" + ques_idx;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while (rs.next()) {
				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
			}
			answer.setQnaDTO(false, index, "admin", title, content, date);
			answer.setWriterName("admin");
			answer.setQuestionIndex(ques_idx);
			return answer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// questionIndex로 AnswerIndex 가져오기
	public static String getAnswerIndexByQuestionIndex(Connection con, String questionIndex) {
		String sql = "SELECT answer_index from Answer where Question_ques_index=" + questionIndex;

		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				String ans_index = rs.getString(1);
				return ans_index;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 내(index)가 작성한 Question에 달린 Answer 가져오기 (이전 이름 = infoAnswerIndex)
	public static ArrayList<QnaDTO> getAnswerByQuestionUserIndex(Connection conn, String user_index) {
		// select * from Answer a where a.Question_ques_index in (select q.ques_index
		// from Question q where q.User_user_index = "1" );
		String sql = "select * from Answer a where a.Question_ques_index in (select q.ques_index from Question q where q.User_user_index ="
				+ user_index + ")";
		
		String index = "";
		String title = "";
		String content = "";
		String date = "";
		String question_index = "";

		Statement stmt = null;
		ArrayList<QnaDTO> answerList = new ArrayList<QnaDTO>();
		QnaDTO answer = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				answer = new QnaDTO();
				index = rs.getString(1);
				title = rs.getString(2);
				content = rs.getString(3);
				date = rs.getString(4);
				question_index = rs.getString(5);
				
				answer.setQnaDTO(false, index, "admin", title, content, date);
				answer.setWriterName("admin");
				answer.setQuestionIndex(question_index);		
				
				answerList.add(answer);
			}
			return answerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 등록 */
	// 질문 등록
	public static int addQuestion(Connection conn, int uidx, String title, String content, String date, String qidx) {

		String insert = "INSERT INTO Question(ques_index,ques_title,ques_content,ques_date,User_user_index) VALUES(?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, qidx);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setString(4, date);
			pstmt.setString(5, Integer.toString(uidx));

			pstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	// 답변 등록
	public static int addAnswer(Connection conn, String title, String content, String date, String quesIndex) {
		String insert = "INSERT INTO Answer(answer_title,answer_content,answer_date,Question_ques_index) VALUES(?,?,?,?);";
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, date);
			pstmt.setString(4, quesIndex);

			pstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	/* 삭제 */
	// answerIndex로 answer 삭제하기
	public static void removeAnswerByAnswerIndex(Connection con, String answerIndex) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("delete from Answer where answer_index=?");
			pstmt.setString(1, answerIndex);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

		}
	}

	// questionIndex로 question 삭제하기
	public static void removeQuestionByQuestionIndex(Connection con, String questionIndex) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("delete from Question where ques_index=?");
			pstmt.setString(1, questionIndex);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

		}
	}

	// questionIndex로 answer 삭제하기
	public static Boolean removeAnswerByQuestionIndex(Connection conn, String questionIndex) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("DELETE from Answer where Question_ques_index = ?");
			pstmt.setString(1, questionIndex);
			pstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return null;
	}

	// userIndex로 question 삭제하기
	public static Boolean removeQuestionByUserIndex(Connection conn, String userIndex) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("DELETE from Question where User_user_index = ?");
			pstmt.setString(1, userIndex);
			pstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return null;
	}

	public static String getMaxIndex(Connection conn, String tableName, String column) {
		//String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = '" + tableName
		//		+ "' and table_schema = 'Stockinsight'";
		String sql = "SELECT MAX("+column+")" + " FROM "+tableName;
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				String maxIndex = rs.getString(1);
				if(maxIndex==null) {
					return "0";
				}
				return maxIndex;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getAutoIndex(Connection conn, String tableName) {
		String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = '" + tableName
				+ "' and table_schema = 'Stockinsight'";
		//String sql = "SELECT MAX("+column+")" + " FROM "+tableName;
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				String autoIndex = rs.getString(1);
				return autoIndex;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}