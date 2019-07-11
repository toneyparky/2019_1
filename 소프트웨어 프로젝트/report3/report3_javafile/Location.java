/* 도시 이름, 위도, 경도 정보를 가진 Location 클래스를 작성하고, 도시이름을 ‘키’로 하는 HashMap<String,Location> 켈랙션을 만드시오. 

 * 사용자로부터 입력 받아 4개의 도시를 아래 실행 결과를 참고하여 실행하라. 
 * 그리고 도시의 이름으로 검색하는 프로그램을 작성하라. 
 * 
 * 사용자로부터 이름 받아서 키로 쓰고, 위도 & 경도를 받아서 한 스트링으로 만들어서 값으로 넣자. 
 * 
 */
package report3;
import java.util.*;
public class Location {
	public static void main(String[] args) {
		HashMap<String, String> location = new HashMap<String, String>();
		System.out.println("도시, 경도, 위도를 총 4회 입력하세요.");
		Scanner scanner = new Scanner(System.in);
		//4개의 input을 받습니다. 그리고 해쉬맵에 넣습니다. 
		for(int i = 0; i < 4; i++) {
			String city, longitude, latitude;
			city = scanner.next();
			city = city.replace(",", "");
			longitude = scanner.next();
			longitude = longitude.replace(",", ", ");
			latitude = scanner.next();
			String point = longitude + latitude; //위도와 경도를 합쳐서 하나의 문자열로 만들어준다. 
			location.put(city, point);
		}
		//해시맵에 있는 모든 도시 출력. 
		System.out.println("해시맵에 있는 모든 도시를 출력합니다.");
		Set<String> keys = location.keySet();
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()) {
			String iter_city = iter.next();
			String iter_point = location.get(iter_city);
			System.out.println(iter_city + ", " + iter_point);
		}
		//해시맵에서 키를 검색에서 있다면 값을 출력, 없다면 없다고, 종료를 입력시 종료. 
		String city_search;
		scanner.nextLine(); // scanner buffer clear
		//Scanner search = new Scanner(System.in);
		do{
			System.out.println("도시를 검색합니다.");
			city_search = scanner.nextLine();
			if(city_search.equals("종료")) {
				System.out.println("검색을 종료합니다.");
				break;
			}
			String point_search = location.get(city_search);
			//찾는 도시가 없을 경우 
			if(point_search == null) 
				System.out.println(city_search + " 없습니다.");
			//찾는 도시가 있을 경우 -> 출력! 
			else {
				System.out.println(city_search + " " + location.get(city_search).replace(",", ""));
			}
	
		}while(true);

	}

}
