import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import DAO.ExerciseRecordsDao;
import DAO.ExerciseTypesDao;
import DAO.UsersDao;
import DTO.ExerciseRecords;
import DTO.ExerciseTypes;
import DTO.Users;
public class Program {
	static UsersDao dao = new UsersDao();
	static ExerciseRecordsDao RecordsDao = new ExerciseRecordsDao();
	static ExerciseTypesDao exerciseTypesDao = new ExerciseTypesDao();
	static Scanner sc = new Scanner(System.in);
	static Users user = new Users();
	static HashMap<String, String> hm = null;
	static String username = "";
	public void run() {
		
		while (true) {
			System.out.println("안녕하세요^^");
			System.out.println("아래 메뉴 중 사용하실 서비스 번호를 입력해주세요");
			System.out.println("------------------------------");
			System.out.println("1.사용자 등록");
			System.out.println("2.개인 정보 수정");
			System.out.println("3.운동 종목 조회");
			System.out.println("4.사용자 운동 기록 조회");
			System.out.println("5.운동 기록 시작");
			System.out.println("6.운동 기록 종료");
			System.out.println("7.운동 기록 삭제");
			System.out.println("8.시스템 종료");
			System.out.println("------------------------------");
		
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				addUser();
				break;
			case 2:
				modifyUser();
				break;
			case 3:
				showExerciseList();
				break;
			case 4:
				showRecords();
				break;
			case 5:
				StartExercise();
				break;
			case 6:
				deleteRecord();
				break;
			case 7:
				System.exit(0);
			default:
				System.out.println("잘못된 메뉴를 선택하셨습니다.");
				break;
			}
		}
	}
	
	//1.사용자 등록
	public void addUser() { 
		System.out.println();
		System.out.println("----사용자 추가----");
		System.out.printf("아이디 입력 : ");
		user.setU_ID(sc.nextLine());
		System.out.printf("비밀번호 입력 : ");
		user.setU_PWD(sc.nextLine());
		System.out.printf("이름 입력 : ");
		user.setU_Name(sc.nextLine());
		System.out.printf("몸무게 입력 : ");
		user.setWeight(Double.parseDouble(sc.nextLine()));
		System.out.printf("키 입력 : ");
		user.setHeight(Double.parseDouble(sc.nextLine()));
		System.out.printf("성별 입력(M/W) : ");
		user.setGender(sc.nextLine());
		int insertrow = dao.addUser(user);
		if (insertrow > 0) {
			System.out.println("----사용자가 추가되었습니다.----");
			System.out.println();
		} else {
			System.out.println("아이디가 중복되었습니다. 프로그램을 종료합니다");
		}
	}
	
	//2.개인 정보 수정
	public void modifyUser() { 
		System.out.println("아이디를 입력하세요!");
		user.setU_ID(sc.nextLine());
		System.out.println("몸무게를 입력하세요!( kg )");
		user.setWeight(Double.parseDouble(sc.nextLine()));
		System.out.println("키를 입력하세요!( cm )");
		user.setHeight(Double.parseDouble(sc.nextLine()));
		System.out.println("성별을 입력하세요!( M / W )");
		user.setGender(sc.nextLine());
		int updaterow = dao.updateUsers(user);
		if (updaterow > 0) {
			System.out.println("update 성공!");
		} else {
			System.out.println("update fail...");
		}
	}
	
	
	//3.운동 종목 조회
	
	public void showExerciseList() {
		System.out.println("운동목록 조회");
		exerciseTypesDao.ExerciseTypesmenue();
		
		}
	
	
	//4.운동 기록 조회
	
	public void showRecords() {
		System.out.println("아이디를 입력하세요");
		username = sc.nextLine();
		System.out.println("조회 옵션을 선택하세요: (1. 전체조회 2. 기간조회)");
		int n = Integer.parseInt(sc.nextLine());
		switch(n) {
		case 1 :
			try {
				List<ExerciseRecords> recs1 = dao.getRecords(username);
		        int sumTime = 0;
		        int sumCal = 0;
		        for(ExerciseRecords e : recs1) {
		        	sumTime += e.getTotalTime();
		        	sumCal += e.getCalBurned();
		        }
		        System.out.println("총 운동한 시간: " + sumTime);
		        System.out.println("총 소모한 칼로리: " + sumCal);
		        for(ExerciseRecords e : recs1) {
		        	System.out.println(e.toString());
		        }
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		case 2:
			//System.out.println("기간 시작 일시를 입력하세요: (ex) 22-05-05 10:00:00)");
			//String startString = sc.nextLine();
			String startString = "22-05-05 10:00:00";
			
			//System.out.println("기간 종료 일시를 입력하세요: (ex) 22-05-10 12:00:00)");
			//String endString = sc.nextLine();
			String endString = "22-05-10 12:00:00";
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		        Date startdate = sdf.parse(startString);
		        Date enddate = sdf.parse(endString);
		        Timestamp start = new Timestamp(startdate.getTime());
		        Timestamp end = new Timestamp(enddate.getTime());

		        List<ExerciseRecords> recs2 = dao.getRecords(username, start, end);
		        int sumTime = 0;
		        int sumCal = 0;
		        for(ExerciseRecords e : recs2) {
		        	sumTime += e.getTotalTime();
		        	sumCal += e.getCalBurned();
		        }
		        System.out.println("총 운동한 시간: " + sumTime);
		        System.out.println("총 소모한 칼로리: " + sumCal);
		        for(ExerciseRecords e : recs2) {
		        	System.out.println(e.toString());
		        }
		    } catch (ParseException e) {
		        System.out.println("날짜 형식이 잘못되었습니다.");
		    }
			break;
		default:
			System.out.println("잘못된 메뉴를 선택하셨습니다.");
		}
		
	}
	
	
	
	
	//5.운동 기록 시작
	public void StartExercise() {
		System.out.println("운동코드를 입력하세요");
		String eTypeCode = sc.nextLine();
		System.out.println("유저아이디를 입력하세요");
		String u_ID = sc.nextLine();
		RecordsDao.exerciseStart(eTypeCode, u_ID);
	}
	
	
	//6.운동 기록 종료
	public void EndExercise() {
		String id = username;
		RecordsDao.exerciseEnd(id);
	}
	
	
	//6.운동 기록 삭제
	public void deleteRecord() {
		System.out.println("유저아이디를 입력하세요");
		username = sc.nextLine();
		RecordsDao.recordShow(username);
		List<ExerciseRecords> records = RecordsDao.recordShow(username);
		if (records != null && records.size() > 0) {
		    System.out.println("조회 결과:");
		    for (ExerciseRecords record : records) {
		        System.out.println(record);
		    }
		} else {
		    System.out.println("조회 결과가 없습니다.");
		}
		
		System.out.println("삭제하실 운동 기록의 코드를 입력하세요.");
		String recordExcercise = sc.nextLine();		
		
		int rowcount = RecordsDao.recordDelete(recordExcercise);
		if (rowcount > 0) {
			System.out.println("삭제성공");
		} else {
			System.out.println("삭제실패");
		}
	}

}
