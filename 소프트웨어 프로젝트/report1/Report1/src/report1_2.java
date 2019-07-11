//천원 단위로 입력받은 소득을 통해 소득세와 소득세의 10%인 지방세를 구하는 프로그램
//종합소득세율표 : https://blog.naver.com/oktax13/221212481844
//2019.03.21.목
//소프트웨어 프로젝트 수업 과제 1-2

import java.util.Scanner;

public class report1_2 {

	public static void main(String[] args) {

		float income_tax = 0; //소득세 
		
		System.out.println("소득금액을 천원단위로 입력하시오>");
		Scanner scanner = new Scanner(System.in);
		float input_money = (float)Integer.parseInt(scanner.next());  
		
		scanner.close();

		if(input_money <= 12000)
			income_tax = (float) (input_money*0.06);
		else if(input_money <= 46000)
			income_tax = ((float) (input_money*0.15)) - 1080;
		else if(input_money <= 88000)
			income_tax = ((float) (input_money*0.24)) - 5220;
		else if(input_money <= 150000)
			income_tax = ((float) (input_money*0.35)) - 14900;
		else if(input_money <= 300000)
			income_tax = ((float) (input_money*0.38)) - 19400;
		else if(input_money <= 500000)
			income_tax = ((float) (input_money*0.40)) - 25400;
		else
			income_tax = ((float) (input_money*0.42)) - 35400;

		
		float local_tax = income_tax/10; //지방세 

		
		System.out.println((int)input_money + "천원의 소득세는 " + (int)income_tax + "천이고, 지방세는 " + (int)local_tax + "천원 입니다.");
	}

}
