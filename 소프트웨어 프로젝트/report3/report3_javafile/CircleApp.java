package report3;
/* 두 원의 정보를 나타내고, 같은지 다른지를 표시하는 프로그램. 
 * 두 원의 객체를 만들고 각 원에 중심과 반지름을 인자로 준다.
 * 원의 정보를 출력한다. 
 * 원이 같은지 비교를 한다.
 */
public class CircleApp {

	public static void main(String[] args) {
		Circle a = new Circle(2,3,5); // 중심 (2,3)에 반지름 5인 원
		Circle b = new Circle(2,3,30); // 중심 (2,3)에 반지름 30인 원
		System.out.println("원 a : " + a);
		System.out.println("원 b : " + b);
		if(a.equals(b)) 
			System.out.println("같은 원");
		else 
			System.out.println("서로 다른 원");
	}
}
