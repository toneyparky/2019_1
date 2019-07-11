//연산자와 값 설정 출력을 하는 클래스 입니다. 중첩클래스를 사용했습니다. 패키지가 다르므로 다른 패키지에서 이용하려 public을 해주었습니다.  

package pkg2;

public class CalcOper {
	
	
	public class Add {
		int a, b;
		//인자 저장 
		public void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		public int calculate() {
			return this.a + this.b;
		}
		
	}
	
	public class Sub{
		int a, b;
		//인자 저장 
		public void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		public int calculate() {
			return this.a - this.b;
		}
		
		
		
	}
	
	public class Mul{
		int a, b;
		//인자 저장 
		public void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 
		public int calculate() {
			return this.a * this.b;
		}
		
		
	}
	
	public class Div{
		int a, b;
		//인자 저장 
		public void setValue(int A, int B) {
			this.a = A;
			this.b = B;
		}
		//연산 	
		public int calculate() {
			return this.a / this.b;
		}
		
		
	}	
		
	
}


