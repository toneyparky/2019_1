//60갑자로 무슨 해인지 출력하는 프로그램
//60갑자표 : http://cafe.naver.com/ladybooupmall/71691 
//2019.03.21.목
//소프트웨어 프로젝트 수업 과제 1-3

import java.util.Scanner;

public class report1_3 {

	public static void main(String[] args) {
		
		System.out.println("년도를 입력하시오>");
		Scanner scanner = new Scanner(System.in);
		
		int input_year = Integer.parseInt(scanner.next());
		
		scanner.close();
		
		int using_year = input_year - 1984 + 1; //과제에서 출력을 원하는 값인 1995, 2000, 2019년이 1984~(1984+60)년을 벗어나지 않기 때문에 제한한다.
											    //+1은 계산상 필요하다.
		
		
		int sibgan, sibeeji; //십간, 십이지 변수 선언 
		
		if(using_year >= 10)
			sibgan = using_year % 10;
		else 								//예외처리 
			sibgan = using_year;
			if(sibgan == 0)
				sibgan = 10;
		
		if(using_year >= 12)
			sibeeji = using_year % 12;
		else 								//예외처리 
			sibeeji = using_year;
			if(sibeeji == 0)
				sibeeji = 12;
		
		String sibgan_arr[] = {"갑", "을", "병", "정", "무", "기", "경", "신", "임", "계"};
		String sibeeji_arr[] = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};
		
		System.out.println(input_year + "년은 " + "\"" + sibgan_arr[sibgan - 1] + sibeeji_arr[sibeeji - 1] + "\"년입니다.");
		
	}

}
