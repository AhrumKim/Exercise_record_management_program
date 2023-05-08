package DAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DTO.ExerciseRecords;
import UTILS.ConnectionHelper;


public class ExerciseRecordsDao {
	// 운동시작 ( 기록 측정 ) insert
	public void exerciseStart(String eTypeCode, String u_ID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionHelper.getConnection("oracle","kosa","1004");
			String sql = "INSERT INTO ExerciseRecords (ERecordID, ETypeCode, U_ID) "
					+ "VALUES ('ER' || LPAD(ExerciseRecordsSeq.NEXTVAL, 5, '0'), ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eTypeCode);
			pstmt.setString(2, u_ID);
			@SuppressWarnings("unused")
			int result = pstmt.executeUpdate();
			System.out.println("운동이 시작되었습니다.");
			System.out.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.close(conn);
			ConnectionHelper.close(pstmt);
		}
	}
	// 운동종료 ( 측정 종료 ) update
	public void exerciseEnd(String u_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CallableStatement cstmt = null;
		String eRecordID = null;
		try {
			conn = ConnectionHelper.getConnection("oracle","kosa", "1004");
			String sql = "select erecordid from ExerciseRecords where U_ID = ? and endtime is null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eRecordID = rs.getString(1);
			}
			System.out.println("운동이 종료되었습니다.");
			System.out.println();
			cstmt = conn.prepareCall("{call KOSA.UpdateExerciseRecord(?)}");
			cstmt.setString(1, eRecordID);
			cstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.close(conn);
			ConnectionHelper.close(conn);
			ConnectionHelper.close(cstmt);
		}
	}
	//운동기록조회
	public List<ExerciseRecords> recordShow(String uid) {
	    List<ExerciseRecords> records =null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	   
	    try {
	        conn = ConnectionHelper.getConnection("oracle", "kosa", "1004");
	        String sql = "select r.erecordid,t.etypename,r.starttime,r.endtime,r.totaltime,r.calburned,r.recorddate from\r\n"
	        		+ "ExerciseRecords r join ExerciseTypes t\r\n"
	        		+ "on r.etypecode = t.etypecode\r\n"
	        		+ "where r.u_id= ?\r\n";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, uid);
	        rs = pstmt.executeQuery();
	        records = new ArrayList<>();
	        while (rs.next()) {
	            ExerciseRecords record = new ExerciseRecords();
	            record.setERecordID(rs.getString(1));
	            record.setETypeCode(rs.getString(2)); // 운동 종류 코드
	            record.setStartTime(rs.getTimestamp(3)); // 시작시간
	            record.setEndTime(rs.getTimestamp(4)); // 끝난 시간
	            record.setTotalTime(rs.getInt(5));
	            record.setCalBurned(rs.getInt(6)); // 소수점 이하를 포함하지 않는 경우, getInt() 메소드 사용
	            record.setRecordDate(rs.getTimestamp(7)); // 기록 일자
	            records.add(record);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        ConnectionHelper.close(rs);
	        ConnectionHelper.close(pstmt);
	        ConnectionHelper.close(conn);
	    }
	    return records;
	}
		
	
	//운동기록삭제
	public int recordDelete(String record_code) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowcount=0;
try {
		    conn = ConnectionHelper.getConnection("oracle","kosa","1004");
		    String sql = "DELETE FROM ExerciseRecords WHERE ERecordID = ?";
		    pstmt = conn.prepareStatement(sql);			
		    pstmt.setString(1, record_code);
		    rowcount = pstmt.executeUpdate();
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		} finally {
		    try {
		        conn.close();
		        pstmt.close();	       
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	return rowcount;
	}
	
	
	
}
