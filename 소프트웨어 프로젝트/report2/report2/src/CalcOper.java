//연산자와 값 설정 출력을 하는 클래스 입니다. 중첩클래스를 사용했습니다. 같은 패키지 내이므로 public을 안해주어도 괜찮습니다.
public class CalcOper {
	
	
	public class Add {
		int a, b;
		//인자저장 
		void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		int calculate() {
			return this.a + this.b;
		}
		
	}
	
	class Sub{
		int a, b;
		//인자저장 
		void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		int calculate() {
			return this.a - this.b;
		}
		
		
		
	}
	
	class Mul{
		int a, b;
		//인자저장 
		void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		int calculate() {
			return this.a * this.b;
		}
		
		
	}
	
	class Div{
		int a, b;
		//인자저장 
		void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		int calculate() {
			return this.a / this.b;
		}
		
		
	}	
		
	
}


