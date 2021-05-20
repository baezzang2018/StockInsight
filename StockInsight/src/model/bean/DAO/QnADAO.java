package model.bean.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/*
public class QnADAO {
	public static int addQuestion(Connection conn, int uidx, String title, String content, String date) {
		// index 諛쏆븘�삤湲�
		String selectSql = "SELECT MAX(ques_index) FROM Question";
		Statement st;
		int number = -1;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectSql);

			System.out.println(rs.toString());
			while (rs.next()) {
				number = rs.getInt(1) + 1;
				System.out.println(number);
			}
			if (number > -1) {
				// 諛쏆븘�삩 index+1濡� insert
				PreparedStatement pstmt = null;
				try {
					conn.setAutoCommit(false);
					// INSERT INTO Stockinsight.Question VALUES('','臾몄쓽�뱶由쎈땲�떎.','移댁뭅�삤 �꼫臾� �넂寃� �삁痢≫븯�뒗嫄�
					// �븘�땶媛��슂?','20201115',null,'1');
					String sqlSt = "INSERT INTO Question VALUES(?,?,?,?,?,?)";
					pstmt = conn.prepareStatement(sqlSt);
					pstmt.setString(1, Integer.toString(number));
					pstmt.setString(2, title);
					pstmt.setString(3, content);
					pstmt.setString(4, date);
					pstmt.setString(5, null);
					pstmt.setString(6, Integer.toString(uidx));

					pstmt.executeUpdate();
					conn.commit();
					conn.setAutoCommit(true);

					return number;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// �떎�뙣�븯硫� -1
		return -1;

	}
	
	public static Boolean checkAdmin(Connection conn, int uidx) {
		// admin�씠硫� true, admin�븘�땲硫� false
		String selectSql = "SELECT user_admin FROM User WHERE user_index="+Integer.toString(uidx);

		Statement st;
		int number = -1;

		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectSql);

			System.out.println(rs.toString());
			while (rs.next()) {
				number = rs.getInt(1);
				if(number==1) {
					return true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return false;

	}
	
	public static int addAnswer(Connection conn, String title, String content, String date) {
		// answer table�뿉 �꽔湲�
		// answer_index 諛섑솚
		String selectSql = "SELECT MAX(answer_index) FROM Answer";
		Statement st;
		int number = -1;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(selectSql);

			System.out.println(rs.toString());
			while (rs.next()) {
				number = rs.getInt(1) + 1;
				System.out.println(number);
			}
			if (number > -1) {
				// 諛쏆븘�삩 index+1濡� insert
				PreparedStatement pstmt = null;
				try {

					conn.setAutoCommit(false);
					// INSERT INTO Stockinsight.Answer  VALUES('','�떟蹂��뱶由쎈땲�떎.','�떟蹂��궡�슜','2020.11.15');
					String sqlSt = "INSERT INTO Answer VALUES(?,?,?,?)";
					pstmt = conn.prepareStatement(sqlSt);
					pstmt.setString(1, Integer.toString(number));
					pstmt.setString(2, title);
					pstmt.setString(3, content);
					pstmt.setString(4, date);

					pstmt.executeUpdate();
					conn.commit();
					conn.setAutoCommit(true);

					return number;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// �떎�뙣�븯硫� -1
		return -1;
	}

	public static Boolean updateQuestion(Connection conn, int question_index, int answer_index) {
		// Question DB�뿉 question_index row�쓽 Answer_answer_index �닔�젙
		// update Stockinsight.Question SET Answer_answer_index=2 where ques_index=1;
		System.out.println("updateQuestion:"+question_index+" "+answer_index);
		String selectSql = "SELECT * FROM Question WHERE ques_index="+ question_index;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet uprs = stmt.executeQuery(selectSql);
			while(uprs.next()) {
				conn.setAutoCommit(false);

				uprs.updateString("Answer_answer_index",Integer.toString(answer_index));
				uprs.updateRow();

				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<QnAList> getPostList(Connection conn){
		ArrayList<QnAList> qnaList = new ArrayList<QnAList>();

		String qu_title="";
		String qu_content="";
		String qu_date="";
		String qu_writer="";
		String qu_index="";
		String qu_reply="";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Question order by ques_index DESC";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				QnAList post = new QnAList();
				QnAList post_answer = new QnAList();
				// index, title, content, date, answer_index, user_index
				qu_index = rs.getString(1);
				qu_title= rs.getString(2);
				qu_content= rs.getString(3);
				qu_date= rs.getString(4);
				qu_reply= rs.getString(5);
				qu_writer= rs.getString(6);
				// writer (index)로 사용자 id 받아오기
				qu_writer = getUserId(conn, qu_writer);

				if(qu_writer!=null) {
					post.setQnAList(true, qu_index, qu_writer, qu_title, qu_content, qu_date);
					// question_post를 list에 넣기
					qnaList.add(post);
					// reply가 null이 아니면  reply_post 받아오기
					if(qu_reply!=null) {
						post_answer = getAnswerPost(conn, qu_reply);
						post_answer.setIndex(qu_index);
						// reply_post를 list에 넣기
						qnaList.add(post_answer);
					}
				}
			}
			return qnaList;
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return qnaList;
	}

	public static QnAList getAnswerPost(Connection conn, String idx) {
		QnAList answer = new QnAList();
		String an_title="";
		String an_content="";
		String an_date="";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Answer WHERE answer_index="+idx;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				an_title = rs.getString(2);
				an_content = rs.getString(3);
				an_date = rs.getString(4);
			}
			answer.setQnAList(false, idx, "admin", an_title, an_content, an_date);
			return answer;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static QnAList getQuestionPost(Connection conn, String idx) {
		Statement stmt = null;
		String questionQuery = "SELECT * FROM Question WHERE ques_index="+idx;
		String qu_title="";
		String qu_content="";
		String qu_date="";
		String qu_writer="";
		String qu_index="";
		String qu_reply="";

		try {
			QnAList post = new QnAList();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				// index, title, content, date, answer_index, user_index
				qu_index = rs.getString(1);
				qu_title= rs.getString(2);
				qu_content= rs.getString(3);
				qu_date= rs.getString(4);
				qu_reply= rs.getString(5);
				qu_writer= rs.getString(6);
				// writer (index)로 사용자 id 받아오기
				qu_writer = getUserId(conn, qu_writer);

				if(qu_writer!=null) {
					// question_post를 list에 넣기
					post.setQnAList(true, qu_index, qu_writer, qu_title, qu_content, qu_date);
				}
			}
			return post;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QnAList getAnswerPostUseQuesIdx(Connection conn, String ques_idx) {
		Statement stmt = null;
		String questionQuery = "SELECT Answer_answer_index FROM Question WHERE ques_index="+ques_idx;

		String ans_idx="";
		try {
			QnAList post = new QnAList();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				ans_idx = rs.getString(1);
			}
			post = getAnswerPost(conn, ans_idx);
			post.setIndex(ques_idx);
			System.out.println("getAPUQI: "+post);
			return post;			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<QnAList> getmyPostList(Connection conn, String param_user_index){
		ArrayList<QnAList> qnaList = new ArrayList<QnAList>();

		String qu_title="";
		String qu_content="";
		String qu_date="";
		String qu_writer_index = "";
		String qu_writer="";
		String qu_index="";
		String qu_reply="";

		Statement stmt = null;
		String questionQuery = "SELECT * FROM Question order by ques_index DESC";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(questionQuery);
			while(rs.next()) {
				QnAList post = new QnAList();
				QnAList post_answer = new QnAList();
				// index, title, content, date, answer_index, user_index
				qu_index = rs.getString(1);
				qu_title= rs.getString(2);
				qu_content= rs.getString(3);
				qu_date= rs.getString(4);
				qu_reply= rs.getString(5);
				qu_writer_index= rs.getString(6);
				// writer (index)로 사용자 id 받아오기
				qu_writer = getUserId(conn, qu_writer_index);

				if(qu_writer!=null && qu_writer_index.contentEquals(param_user_index)) {
					post.setQnAList(true, qu_index, qu_writer, qu_title, qu_content, qu_date);
					// question_post를 list에 넣기
					qnaList.add(post);
					// reply가 null이 아니면  reply_post 받아오기
					if(qu_reply!=null) {
						post_answer = getAnswerPost(conn, qu_reply);
						post_answer.setIndex(qu_index);
						// reply_post를 list에 넣기
						qnaList.add(post_answer);
					}
				}
			}
			return qnaList;
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return qnaList;
	}

	// 질문글 인덱스로 답변글 인덱스 받아오기
		public static String find_answerIndex(Connection con, String que_index) {
			String sql = "SELECT Answer_answer_index from Question where ques_index=" + que_index;

			Statement st;
			try {
				st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while (rs.next()) {
					String ans_index = rs.getString(1);				
					return ans_index;

				}
			}catch(SQLException e) {
				e.printStackTrace();
			}

			return null;		
		}

		// 답변글 인덱스로 답변글 삭제하기
		public static void removeAnswerIndex(Connection con, String answer_index) throws SQLException {
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("delete from Answer where answer_index=?");
				pstmt.setString(1, answer_index);
				pstmt.executeUpdate();
				
				con.commit();
				con.setAutoCommit(true);

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				if (pstmt != null) {pstmt.close();}

			}
		}

		// 질문글 인덱스로 질문글 삭제하기
		public static void removeQuestionIndex(Connection con, String que_index) throws SQLException {
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("delete from Question where ques_index=?");
				pstmt.setString(1, que_index);
				pstmt.executeUpdate();

				con.commit();
				con.setAutoCommit(true);

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				if (pstmt != null) {pstmt.close();}

			}
		}
		
		// 질문글 index로 질문글 table의 답변 null로 수정
		public static void updateAnswerIndex(Connection con, String que_index) {
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("UPDATE Question SET Answer_answer_index=null WHERE ques_index=?");
				pstmt.setString(1, que_index);
				pstmt.executeUpdate();

				con.commit();
				con.setAutoCommit(true);

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

		public static ResultSet infoAnswerIndex(Connection conn, String user_index) {
			String sql = "SELECT Answer_answer_index from Stockinsight.Question where User_user_index =";
			Statement st;

			try {
				st = conn.createStatement();

				if (st.execute(sql + "'" + user_index + "'")) {
					return st.getResultSet();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return null;     
		}

		public static Boolean rmAnswer(Connection conn, String answer_index) throws SQLException {
			PreparedStatement pstmt = null;
			System.out.println("디비유틸 엔설: "+ answer_index);
			try {
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement("DELETE from Answer where answer_index = ?");
				pstmt.setString(1, answer_index);
				pstmt.executeUpdate();

				conn.commit();
				conn.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				if (pstmt != null) {pstmt.close();}
			}
			return null;
		}
		
		public static Boolean rmQuestion(Connection conn, String user_index) throws SQLException {
			PreparedStatement pstmt = null;

			try {
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement("DELETE from Question where User_user_index = ?");
				pstmt.setString(1, user_index);
				pstmt.executeUpdate();

				conn.commit();
				conn.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				if (pstmt != null) {pstmt.close();}
			}
			return null;
		}

}*/
