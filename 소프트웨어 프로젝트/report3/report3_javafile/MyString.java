package report3;
/* MyString클래스를 구현하기 : String 클래스와 유사한 나만의 문자열 클래스.
 * 
 * 출력 (println) - MyString, int, boolean을 받을 수 있도록 오버로딩. 
 * 길이 (length)
 * 같은지 여부 (equals)
 * 해당 인덱스에 어떤 값이 있는지 (charAt)
 * 대문자로 (toUppercase)
 * 인덱스로 substring만들기 (substring)
 * 객체 생성 안하고 바로 들어가는 인자 반환하기 (valueOf)
 */
public class MyString {
	//변수 설정.
	public char[] mystring;
	//생성자 .
	public MyString(char[] input) {
		this.mystring = input;	
	}
	//println 메서드 (각각 MyString, int, boolean을 인자로 받는다.) 
	public static void println(MyString input) {
		System.out.println(input.mystring);
	}
	public static void println(int input) {
		System.out.println(input);
	}
	public static void println(boolean input) {
		System.out.println(input);
	}
	//length 메서드 (길이 반환)
	public int length() {
		return mystring.length;
	}
	//equals 메서드 (같은지 여부 반환)
	public boolean equals(MyString check) {
		String str_input = String.valueOf(mystring);
		String str_check = String.valueOf(check.mystring);
		if(str_input.equals(str_check))
			return true;
		else
			return false;
	}
	//charAt 메서드 (인자로 받은 인덱스값에 해당하는 값을 반환)
	public char charAt(int index) {
		return mystring[index];
	}
	//toUppercase 메서드 (대문자로 변환 후 반환)
	public MyString toUppercase() {
		char [] temp_char = new char[mystring.length];
		for(int i = 0; i < mystring.length; i++) {
			temp_char[i] = Character.toUpperCase(mystring[i]);
		}
		MyString temp = new MyString(temp_char);
		return temp;	
	}
	//substring 메서드 (받은 두 인덱스 사이의 값을 출력)
	public MyString substring(int ind1, int ind2) {
		char [] temp_char = new char[(ind2 - ind1) + 1];
		for(int i = 0; i < (ind2 - ind1) + 1; i++) {
			temp_char[i] = mystring[ind1 + i];
		}
		MyString temp = new MyString(temp_char);
		return temp;
	}
	//valueOf 메서드 (input으로 받은 값을 넘겨준다. - int, boolean)
	public static int valueOf(int input) {
		return input;
	}
	public static boolean valueOf(boolean input) {
		return input;
	}

}
