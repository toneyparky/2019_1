//연산을 하는 클래스입니다.

import java.util.Scanner;

public class Calc {

	public static void main(String[] args) {
		int num1, num2;
		String oper;
		while(true) {
			System.out.println("두 정수와 연산자를 입력하시오. :");
			Scanner scanner = new Scanner(System.in);
			num1 = Integer.parseInt(scanner.next());
			oper = scanner.next();
			num2 = Integer.parseInt(scanner.next());
			
			//+ 를 보면 덧셈, CalcOper로 연결해서 Add 
			if(oper.equals("+")){
				CalcOper outer = new CalcOper();
				CalcOper.Add add = outer.new Add();
				add.setValue(num1, num2);
				System.out.println(add.calculate());
			}
			//-를 보면 뺄셈, CalcOper로 연결해서 Sub 
			else if(oper.equals("-")){
				CalcOper outer = new CalcOper();
				CalcOper.Sub sub = outer.new Sub();
				sub.setValue(num1, num2);
				System.out.println(sub.calculate());
			}
			//*를 보면 곱셈, CalcOper로 연결해서 Mul 
			else if(oper.equals("*")){
				CalcOper outer = new CalcOper();
				CalcOper.Mul mul = outer.new Mul();
				mul.setValue(num1, num2);
				System.out.println(mul.calculate());
			}	
			///를 보면 나눗셈, CalcOper로 연결해서 Div
			else if(oper.equals("/")){
				CalcOper outer = new CalcOper();
				CalcOper.Div div = outer.new Div();
				div.setValue(num1, num2);
				System.out.println(div.calculate());
			}
		}
	
	}

}

