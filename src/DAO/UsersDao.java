package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DTO.ExerciseRecords;
import DTO.Users;
import UTILS.ConnectionHelper;

public class UsersDao {
		
	Scanner sc = new Scanner(System.in);
	
	//사용자 정보 수정하기
	public int updateUsers(Users user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rownum=0;
		
		try {
			conn = ConnectionHelper.getConnection("oracle", "KOSA", "1004");
			String sql = "UPDATE users SET weight=?, height=?, gender=? where U_ID=?";
			pstmt = conn.prepareStatement(sql);
					
			pstmt.setDouble(1, user.getWeight());
			pstmt.setDouble(2, user.getHeight());
			pstmt.setString(3, user.getGender());
			pstmt.setString(4,  user.getU_ID());
			
			
			rownum = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			ConnectionHelper.close(pstmt);
			ConnectionHelper.close(conn);
		}
		return rownum;
	}
	
		// 사용자 정보 추가하기
	public int addUser(Users user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowcount = 0;
		try {
		conn = ConnectionHelper.getConnection("oracle","kosa","1004");
		String sql = "INSERT INTO users(U_ID, U_PWD, U_NAME, Weight, Height, Gender) VALUES(?, ?, ?, ?, ?, ?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getU_ID());
		pstmt.setString(2, user.getU_PWD());
		pstmt.setString(3, user.getU_Name());
		pstmt.setDouble(4, user.getWeight());
		pstmt.setDouble(5, user.getHeight());
		pstmt.setString(6, user.getGender());
		rowcount = pstmt.executeUpdate();
		System.out.println(rowcount);
		} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
		} finally {
		ConnectionHelper.close(pstmt);
		ConnectionHelper.close(conn);
		}
		return rowcount;
			} 
		
public List<ExerciseRecords> getRecords(String U_ID) {
	List<ExerciseRecords> RecordList = new ArrayList<>();
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		conn = ConnectionHelper.getConnection("oracle", "KOSA", "1004");
		String sql = "SELECT * FROM ExerciseRecords WHERE U_ID=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, U_ID);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			ExerciseRecords allRecords = new ExerciseRecords();
			
			allRecords.setU_ID(U_ID);
            allRecords.setERecordID(rs.getString("ERecordID"));
            allRecords.setETypeCode(rs.getString("ETypeCode"));
            allRecords.setStartTime(rs.getTimestamp("startTime"));
            allRecords.setEndTime(rs.getTimestamp("endTime"));
            allRecords.setTotalTime(rs.getInt("totalTime"));
            allRecords.setCalBurned(rs.getInt("calBurned"));
            allRecords.setRecordDate(rs.getTimestamp("RecordDate"));
            
            RecordList.add(allRecords);
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	} finally {
		ConnectionHelper.close(rs);
		ConnectionHelper.close(pstmt);
		ConnectionHelper.close(conn);
	}
	return RecordList;
}

//운동통계 - 기간별(한달, 일주일)
public List<ExerciseRecords> getRecords(String U_ID, Timestamp startdate, Timestamp enddate) {
	List<ExerciseRecords> RecordList = new ArrayList<>();
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		conn = ConnectionHelper.getConnection("oracle", "KOSA", "1004");
		String sql = "SELECT * FROM ExerciseRecords WHERE U_ID=? AND RecordDate BETWEEN ? AND ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, U_ID);
		pstmt.setTimestamp(2, startdate);
		pstmt.setTimestamp(3, enddate);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			ExerciseRecords allRecords = new ExerciseRecords();
			
			allRecords.setU_ID(U_ID);
            allRecords.setERecordID(rs.getString("ERecordID"));
            allRecords.setETypeCode(rs.getString("ETypeCode"));
            allRecords.setStartTime(rs.getTimestamp("startTime"));
            allRecords.setEndTime(rs.getTimestamp("endTime"));
            allRecords.setTotalTime(rs.getInt("totalTime"));
            allRecords.setCalBurned(rs.getInt("calBurned"));
            allRecords.setRecordDate(rs.getTimestamp("RecordDate"));
            
            RecordList.add(allRecords);
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	} finally {
		ConnectionHelper.close(rs);
		ConnectionHelper.close(pstmt);
		ConnectionHelper.close(conn);
	}
	return RecordList;
}
}


		
	


