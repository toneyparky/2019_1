//PairMap을 상속받은 Dictionary 클래
import java.util.Arrays;

public class Dictionary extends PairMap{
	
	//인덱스로 사용할 변수 
	public int key_index = -1, value_index = -1;
	
	//생성자! PairMap의 keyArray, vlaueArray를 생성 및 초기화. 
	public Dictionary() {
		keyArray = new String[100];
		valueArray = new String[100];
	}
	
	@Override
	//key를 입력받아 그에 맞는 value를 출력해주는 메서드! 
	String get(String key) {
		int found_index = Arrays.asList(keyArray).indexOf(key);
			if(found_index == -1) {
				return null;
			}
			else {
				return valueArray[found_index];
			}
	}

	@Override
	//key value를 받아서 저장하는 메서드! 
	void put(String key, String value) {
		//key와 value 인덱스를 하나씩 올린다. 
		this.key_index += 1;
		this.value_index += 1;
		//올린 인덱스에 저장한다. 
		this.keyArray[this.key_index] = key;
		this.valueArray[this.value_index] = value;

		//input key가 이미 저장이 되어있다면 추가로 저장하지 않고 value만 바꾸어주는 코드 
		for(int i = 1; i < this.key_index + 1; i++) {
			if(keyArray[this.key_index] == key) {
				valueArray[this.key_index] = value;
				this.key_index -= 1;
				value_index -= 1;
				break;
			}
		}	
	}

	@Override
	String delete(String key) {
		//key가 이미 저장이 되어있는지 확
		int found_index = Arrays.asList(keyArray).indexOf(key);
		//저장이 안되어있다면..
		if(found_index == -1) {
			return null;
		}
		//삭제하는 부분 
		else { 
			//temp로 잠시 저장 
			String return_val = valueArray[found_index]; //이게 레퍼런스라서 안될 수도 있음!!!!!  -> 된다!
			valueArray[found_index] = null;
			return return_val;
		}
	}

	@Override
	//길이를 구하는 메서드 
	int length() {
		return this.key_index;
	}
}

//추상 클래스인 PairMap
abstract class PairMap {
	protected String keyArray []; // key들을 저장하는 배열
	protected String valueArray []; // value 들을 저장하는배열
	abstract String get(String key); // key 값으로 value를 검색
	abstract void put(String key, String value); // key와 value를 쌍으로 저장
	abstract String delete(String key); // key 값을 가진 아이템(value와 함께)을 삭제. 삭제된 value 값 리턴
	abstract int length(); // 현재 저장된 아이템의 개수 리턴
}
