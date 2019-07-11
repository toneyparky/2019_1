//Dictionary class를 이용할 DictionaryApp class
public class DictionaryApp {

	public static void main(String[] args) {

		Dictionary dic = new Dictionary();
		dic.put("황기태", "자바");
		dic.put("이재문", "파이선");
		dic.put("이재문", "C++"); // 이재문의 값을 C++로 수정		
		System.out.println("이재문의 값은 " + dic.get("이재문"));
		System.out.println("황기태의 값은 " + dic.get("황기태"));
		dic.delete("황기태");		
		System.out.println("황기태의 값은 " + dic.get("황기태"));
		
	}

}
