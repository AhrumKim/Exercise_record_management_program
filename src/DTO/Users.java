package DTO;

import lombok.Data;

@Data
public class Users {

	private String U_ID;		// 사용자아이디
	private String U_PWD;		// 사용자암호
	private String U_Name;		// 사용자이름
	private double weight;			// 몸무게
	private double height;			// 키
	private String gender;		// 성별
	
	
}
