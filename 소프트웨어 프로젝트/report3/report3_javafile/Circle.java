package report3;
/* Circle class
 * 생성자에 인자를 3 개 받아 각각 원 중심의 x, y 그리고 반지름을 저장한다.
 * 
 * 객체를 이용해 출력을 할 시 출력할 내용을 toString() 메서드를 이용해 지정해준다.
 * 
 * 두 원의 중심이 같은지 여부를 boolean으로 확인해주는 equals() 메서드도 만든다.
 */
import java.lang.String;

public class Circle {
	
	int x, y, radius;
	//생성
	public Circle(int a, int b, int c) {
		this.x = a;
		this.y = b;
		this.radius = c;
	}
	
	//객체를 이용해 출력시 멘트 지정. 
	public String toString() {
		return "Circle(" + this.x + "," + this.y + ") 반지름" + this.radius;
	}
	
	//두 원의 중심이 같은지 여부. 
	public boolean equals(Circle circle) {
		boolean result;
		if(this.x == circle.x && this.y == circle.y)
			result = true;
		else
			result = false;
		return result;
	}
	
}
