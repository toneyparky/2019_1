//report 2-1 : 사각형을 나타내는 Rectangle 클래스를 작성하라. 
public class Rectangle {
	//초기 변수 선 및 초기화 
	double width = 1;
	double height = 1;
	String color = "white";
	
	//인자 없는 생성자 
	public Rectangle() {
		this.width = 1;
		this.height = 1;
	}
	//인자 1개인 생성자 
	public Rectangle(double num) {
		this.width = num;
		this.height = num;
	}
	//인자 2개인 생성자 
	public Rectangle(double num1, double num2) {
		this.width = num1;
		this.height = num2;
		
	}
	//넓이 구하는 메서드 
	double getArea() {
		double area =this.width * this.height;
		return area;
	}
	//둘레 구하는 메서드 
	double getPerimeter() {
		double perimeter = 2 * (this.width + this.height);
		return perimeter;
	}
	
	public static void main(String[] args) {
		
		//3개의 Rectangle 객체 배열을 만든다. 
		Rectangle[] recArray = new Rectangle[3];
		
		recArray[0] = new Rectangle();
		recArray[1] = new Rectangle(2.5);
		recArray[2] = new Rectangle(3.3, 2.2);
		
		//출력 
		System.out.println("recArray[0]의 넓이: " + recArray[0].getArea());
		System.out.println("recArray[0]의 둘레: " + recArray[0].getPerimeter());
		System.out.println("recArray[1]의 넓이: " + recArray[1].getArea());
		System.out.println("recArray[1]의 둘레: " + recArray[1].getPerimeter());
		System.out.println("recArray[2]의 넓이: " + recArray[2].getArea());
		System.out.println("recArray[2]의 둘레: " + recArray[2].getPerimeter());
	}
}
