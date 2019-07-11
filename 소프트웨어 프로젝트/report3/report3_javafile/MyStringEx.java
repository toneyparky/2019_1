package report3;
/* MyString을 구동할 MyStringEx 클래스. 
 * MyString 객체 s, s1, s2에 각각 char배열로 초기화한 것을 생성.
 * 출력 (println)
 * 길이 (length)
 * 같은지 여부 (equals)
 * 해당 인덱스에 어떤 값이 있는지 (charAt)
 * 대문자로 (toUppercase)
 * 인덱스로 substring만들기 (substring)
 * 객체 생성 안하고 바로 들어가는 인자 반환하기 (valueOf)
 */
public class MyStringEx {

   public static void main(String[] args) {
	MyString s = new MyString(new char[] {'a', 'B', 'c', 'D'});
	MyString s1 = new MyString(new char[] {'a', 'B', 'c', 'D'});
	MyString s2 = new MyString(new char[] {'a', 'b', 'c', 'd'});

	System.out.print("s = ");
	MyString.println(s);  // aBcD
	System.out.print("s1 = ");
	MyString.println(s1);  // aBcD
	System.out.print("s2 = ");
	MyString.println(s2);  // abcd
		    
	System.out.println("s.length = " + s.length());  
	System.out.println("s.equals(s1) = " + s.equals(s1)); // true
	System.out.println("s.equals(s2) = " + s.equals(s2)); // false
	System.out.println("s.charAt(1) = " + s.charAt(1));  // B
		    		   
	MyString s3 = s.toUppercase();
	System.out.print("s.toUppercase() = ");
	MyString.println(s3);                // ABCD
			
	MyString s4 = s.substring(1, 2);
	System.out.print("s.substring(1, 2) = ");
	MyString.println(s4);   // Bc
			
	System.out.print("MyString.valueOf(345) = ");
	MyString.println(MyString.valueOf(345));  // 325
				
	System.out.print("MyString.valueOf(true) = ");
	MyString.println(MyString.valueOf(true));   // true	
   }
}
