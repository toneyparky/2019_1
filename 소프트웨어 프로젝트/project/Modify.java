/*
 * 실제 볼링 게임의 기능을 담당하는 클래스.
 * 메인함수와 점수를 구하는 함수(3개), 점수를 계산하는 함수(scoreCal)로 총 3개의 기능으로 구성이 되어있다.
 * 홀수 번 공(randScore_odd)과 짝수 번 공(randScore_even)은 각각 다른 점수를 구하는 함수를 호출한다. 
 * 이를 통해 나온 결과값을 scoreboardUpper에 업데이트하고 이를 이용한다.(점수 판 윗줄 == 각 공의 점수를 나타냄)
 * 추가적으로 마지막 10번째 프레임은 Strike거나 spare라면 공을 한 번 더 던지기에 이를 위한 함수를 따로 사용한다.(randScore_last)
 */

import java.util.Random;
import java.util.Scanner;

public class Modify {
	public static String player = new String(); //사용자의 이름.
	public static String player2 = new String();
	public static int skill; //사용자의 실력. 
	public static int skill2;
	public static Lock start_lock = new Lock(); //처음 시작을 막는 lock.
	public static Lock roll_lock = new Lock(); //차례대로 시작하게 하는 lock.
	public static Lock roll_lock2 = new Lock();
	public static int[] SBU = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //공당 점수 표시.
	public static int[] SBU2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	public static int[] SB = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //프레임당 점수 표시.
	public static int[] SB2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	//홀수 횟수의 공을 던질 때 점수를 구하는 메서드 (매개변수 level은 하수, 중수, 고수를 각각 1, 2, 3으로 나타냄) 
	public int randScore_odd(int level) {
		double a, b, c, d; //a, b, c, d는 각각 점수가 나올 수 있는 확률 
		
		//실력에 맞게 확률 변수 설정 
		if(level == 1) { //40, 35, 15, 10
			a = 0.4;  
			b = 0.75;
			c = 0.90;
			d = 1;
		}
		else if(level == 2) { //15, 30, 40, 15
			a = 0.15;
			b = 0.50;
			c = 0.85;
			d = 1;
		}
		else if(level == 3) { //10, 25, 17, 48
			a = 0.10;
			b = 0.35;
			c = 0.52;
			d = 1;
		}
		else {
			return -1;
		}
		
		Random r = new Random();
		double rand = r.nextDouble();
		//예) level = 1 (하수) : 0~4점 (a = 40%확률), 5~8점(b = 35%확률), 9점(c = 15%확률), 10점(d = 10%확률) 
		if(rand <= a) {
			int score = r.nextInt(5);
			return score;
		}
		else if(rand <= b) {
			int score = (int)(r.nextDouble() * 4 + 5);
			
			return score;
		}
		else if(rand <= c) {
			return 9;
			
		}
		else if(rand <= d) {
			return 10;
		}
		// blank
		else {
			System.out.print("오류 1");
			return -1;
		}
	}
	//짝수 횟수의 공을 던질 때 점수를 구하는 메서드 (매개변수 level은 하수, 중수, 고수를 각각 1, 2, 3으로 나타냄, last_score는 이전에 획득한 점수) 
	public int randScore_even(int level, int last_score) {
		//스페어 처리용 (짝수번 째 던지는 공만)
		//이 메서드에 들어오면 무조건 open 혹은 spare가 결과로 나온다. 
		//실력에 맞게 확률 변수 설정
		//a = no spare = open,  b = yes spare
		double a, b; //a, b는 각각 점수가 나올 수 있는 확률 
		int rest_score = 10 - last_score; //spare로 나올 수 있는 점수 계산을 위해 10에서 이전에 나온 값을 빼둔 값.
		
		//예외처리 : 20번째 던진 공에 한해서는 19번째 공이 strike더라도 이 메서드에 들어와서 아래의 조건문을 거친다. 
		//19번째 공이 strike 
		if(last_score == 10) {
			//20번째 공은 0~10까지 점수를 낼 수 있다. 
			if (last_score == 10) {
				rest_score = 10;
			//20번째 공은 (10 - 19번째 공의 점수)만큼 점수를 낼 수 있다.
			}else {
				rest_score = 10 - last_score;
			}
			//확률 b를 이용하지 않기 위해 a를 1로 설정. 
			System.out.print("이전10 "); //이전점수가 10이였다는 것을 표시하기위해 -> 추후 삭제 예정  
			a = 1;
			b = 0;
		}
		
		//실력에 맞게 확률 변수 설정 
		if(level == 1) { //75, 25
			a = 0.75;  
			b = 1;
		}
		else if(level == 2) { //30, 70
			a = 0.30;
			b = 1;
		}
		else if(level == 3) { //10, 90
			a = 0.1;
			b = 1;
		}//blank 
		else {
			System.out.print("오류 2");
			return -1;
		}
		
		Random r = new Random();
		double rand = r.nextDouble();
		//예) level = 1 (하수) : no spare = open (a = 75%확률), spare (b = 25%확률)
		// spare처리 실패 (앞선 던짐에서 3이 나왔으면 이번에는 무조건 0 ~ 6이 나오게)
		if(rand <= a) { //0 ~ spare처리 가능 직전 점수 까지 랜덤 
			int score = r.nextInt(rest_score);
			return score;
		}
		// 무조건 spare처리 (앞선 던짐에서 3이 나왔으면 이번에는 무조건 7이 나오게) 
		else if(rand <= b) { 
			return rest_score;
		}
		// blank
		else {
			return -1;
		}
	}
	
	//예외처리//
	//21번째 공을 던질 때 점수를 구하는 메서드 (매개변수 level은 하수, 중수, 고수를 각각 1, 2, 3으로 나타냄, throw19, 20은 19, 20번째 던짐에서 획득한 점수) 
	public int randScore_last(int level, int throw19, int throw20) { //throw19와 throw20을 이용해서 상황을 분기한다. 
		double a, b; //a, b는 각각 점수가 나올 수 있는 확률 .
		int rest_score = 10; //초기화.
		//19와 20번째 던짐이 둘 다 10점일 경우 -> 21번째도 0 ~ 10까지 점수 획득 가능.
		if(throw19 == 10) {
			if(throw20 == 10) {
				rest_score = 10;
			}
			//19는 strike 20은 아닐 때 -> 10에서 20던진 점수뺀 남은만큼만 가능.
			else {
				rest_score = 10 - throw20;
			}
		}
		else {
			//19번째의 던짐이 10이 아니고 20번째 던짐이 10점일 경우 (19번째 던짐이 0) -> 21번째도 0 ~ 10점까지 점수 획득 가능.  
			if(throw20 == 10) {
				rest_score = 10;
			//19번째와 20번째 던짐 둘 다 10점이 아닌 경우 -> 0 ~ (10점 - 20번째 던짐에서의 점수)까지 점수 획득 가능. 
			}else {
				rest_score = 10 - throw20;
			}
		}
		
		//실력에 맞게 확률 변수 설정 
		if(level == 1) { //35, 65
			a = 0.35;  
			b = 1;
		}
		else if(level == 2) { //20, 80
			a = 0.2;
			b = 1;
		}
		else if(level == 3) { //10, 90
			a = 0.10;
			b = 1;
		}
		else {
			return -1;
		}
		
		Random r = new Random();
		double rand = r.nextDouble();
		
		//예) level = 1 (하수) : 0 ~ 4 (a = 35%확률), 0 ~ rest_score (b = 65%확률) + rest_score가 5미만이면 그냥 a든 b든 그 이하를 랜덤으로 고름.
		if(rand <= a) {
			if(rest_score < 5) {
				int score = r.nextInt(rest_score);
				return score;
			}
			else {
				int score = r.nextInt(5);
				return score;
			}
		}
		else if(rand <= b) {
			int score = r.nextInt(rest_score);
			return score;
		}
		// blank
		else {
			System.out.print("오류 1");
			return -1;
		}
	}
	
	//계산한 frame의 점수를 scoreboard를 업데이트하는 메서드 
	//count - 몇 번째 공인지, result - 이번 공의 점수, scoreboard - frame별 점수 합산 표시, scoreboardUpper - 공별 점수를 표시, is_strike - frame별로 strike인지 표시, is_spare - frame별로 spare인지 표시. 
	public int[] scoreCal(int count, int result, int[] scoreboard, int[] scoreboardUpper, boolean[] is_strike, boolean[] is_spare) {
		////////////예외처리 첫 번째 공부터 여섯 번째 공까지!!! + 19 번째, 20 번째 공들 (10번째 프레임) ////////////////

		//맨 처음 공 
		if(count == 1) {
			//strike
			if(result == 10) {
				is_strike[0] = true;
				return scoreboard;
			}
			//not strike 
			is_strike[0] = false;
			return scoreboard;
		}
		//두 번째 공  
		else if(count == 2) {
			// frame 1 is spare
			if (scoreboardUpper[0] + scoreboardUpper[1] == 10) {
				is_spare[0] = true;
				return scoreboard;
			}
			//not spare 
			scoreboard[0] = scoreboardUpper[0] + result;
			return scoreboard;
		}
		// 세 번째 공 
		else if(count == 3) {
			//strike
			if(result == 10) {
				is_strike[1] = true;
			}
			else {
				is_strike[1] = false;
			}
			
			//if frame 1 spare, fill the frame 1.
			if(is_spare[0] == true) {
				scoreboard[0] = result + 10;
				is_spare[0] = false; //처리되었으니 false로! 
			}
			return scoreboard;
		}
		// 네 번째 공 
		else if(count == 4) {
			//frame 1 is strike && frame 2 is not strike, fill the frame 1.
			if(is_strike[0] == true && is_strike[1] == false) {
				scoreboard[0] = 10 + scoreboardUpper[2] + result;
				is_strike[0] = false;//처리되었으니 false로! 
			}
			
			//spare
			if(scoreboardUpper[2] + result == 10) {
				is_spare[1] = true;
				return scoreboard;
			}//not spare not strike
			else {
				scoreboard[1] = scoreboard[0] + scoreboardUpper[2] + result;
				is_spare[1] = false;//처리되었으니 false로! 
				return scoreboard;
			}
		}	
		// 다섯 번째 공 
		else if(count == 5) {
			//strike && frame 1 is strike
			if(result == 10) {
				is_strike[2] = true;
				if(is_strike[0] == true && is_strike[1] == true){
					scoreboard[0] = 30;
					is_strike[0] = false;//처리되었으니 false로! 
				}
			}
			
			//not strike && frame 1 is strike
			if(result != 10){
				if(is_strike[0] == true && is_strike[1] == true){
					scoreboard[0] = 20 + result;
					is_strike[0] = false;//처리되었으니 false로! 
				}
				is_strike[2] = false;//처리되었으니 false로! 
			}
			
			//if frame 2 spare 
			if(is_spare[1] == true) {
				scoreboard[1] = scoreboard[0] + result + 10;
				is_spare[1] = false; //처리되었으니 false로! 
			}
			return scoreboard;
		}	
		// 여섯 번째 공 
		else if(count == 6) {
			//frame 2 is strike && frame 3 is not strike
			if(is_strike[1] == true && is_strike[2] == false) {
				scoreboard[1] = scoreboard[0] + 10 + scoreboardUpper[4] + result;
				is_strike[1] = false;
			}
			
			//spare
			if(scoreboardUpper[4] + result == 10) {
				is_spare[2] = true;
				return scoreboard;
			}//not spare not strike
			else {
				scoreboard[2] = scoreboard[1] + scoreboardUpper[4] + result;
				is_spare[2] = false;
				return scoreboard;
			}		
		}
		//마지막 프레임일 때! 19 번째 공 
		else if(count == 19) {
			//strike 
			if(result == 10) {
				is_strike[9] = true;
			}
			
			//frame 8이 strike라면 
			if(is_strike[7] == true) {
				//frame 9가 strike라면 
				if(is_strike[8] == true) { // frame 8을 채운다. 
					scoreboard[7] = scoreboard[6] + 20 + result;
					is_strike[7] = false;
				}
			}
			
			//frame 9가 spare라면
			if(is_spare[8] == true) {
				scoreboard[8] = scoreboard[7] + 10 + result;
				is_spare[8] = false;
			} 
			return scoreboard;	
		}
		//마지막 프레임일 때! 20 번째 공 
		else if(count == 20) {
			//frame 9가 strike라면 
			if(is_strike[8] == true) { //frame 9를 채운다.
				scoreboard[8] = scoreboard[7] + 10 + scoreboardUpper[18] + result;
			}
			
			//frame 10이 spare이면 
			if(scoreboardUpper[18] + result == 10) {
				is_spare[9] = true;
			}//아니면 19,20번째 던진 값을 frame 10에 넣어주고 끝낸다. 
			else {
				is_spare[9] = false;
				scoreboard[9] = scoreboard[8] + scoreboardUpper[18] + result;
			}
			return scoreboard;
		}
		else {
		}

		/////////////////////예외처리 끝///////////////////////
		
		//홀수번째 던진 공 
		if((count % 2) == 1) {
			//strike or not?
			if(result == 10) {
				is_strike[count/2] = true;
				is_spare[count/2] = false;
			}
			else {
				is_strike[count/2] = false;
			}
			
			// 전전이 그리고 전도 strike이면 전전에 10과 10과 result를 넣어줘야함   
			if(is_strike[(count/2) - 1]) {
				if(is_strike[(count/2) - 2]) {
					scoreboard[count/2 - 2] = scoreboard[count/2 - 3] + 20 + result;
					is_strike[count/2 - 2] = false;
				}
			}
			//이전이 spare
			if(is_spare[count/2 - 1]) {
				scoreboard[count/2 - 1] = scoreboard[count/2 - 2] + 10 + result;
				is_spare[count/2 - 1] = false;
			}
			return scoreboard;
		}
		
		//짝수번째 던진 공 
		if((count % 2) == 0) {
			//이전의 frame is strike -> 이번 frame의 두 값을 더해준다!  
			if(is_strike[count/2 - 2]) {
				scoreboard[count/2 - 2] = scoreboard[count/2 - 3] + 10 + scoreboardUpper[count - 2] + result;
				is_strike[count/2 - 2] = false;
			}
			
			//이번 프레임이 spare인지 아닌지 
			if(scoreboardUpper[count - 2] + result == 10) {
				is_spare[count/2 - 1] = true;
			}//spare 아니라면 scoreboard에 해당 프레임 값 업데이트 
			else {
				scoreboard[count/2 - 1] = scoreboard[count/2 - 2] + scoreboardUpper[count - 2] + result;
				is_spare[count/2 - 1] = false;
			}
			return scoreboard;
		}
		
		return scoreboard;	
		
}
	
/*
 * 실행하는 메인함수.
 * count, result, scoreboard, scoreboardUpper is_strike, is_spare 각각 플레이어 1과 플레이어 2용으로 두 개씩 둔다.
 * 각각을 번갈아가면서 실행하여 게임을 진행한다. 
 */	
	public static void main(String[] args) {
		Modify se = new Modify();
		
		int count = 1; //던진 공의 횟수 표시.
		int count2 = 1; 
		int result = 0; //던진 공의 점수 표시.
		int result2 = 0; 
		int[] scoreboard = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //프레임의 합 점수 표시 
		int[] scoreboard2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //프레임의 합 점수 표시 
		int[] scoreboardUpper = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //던질 때마다의 점수 표시.
		int[] scoreboardUpper2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //던질 때마다의 점수 표시.
		boolean[] is_strike = {false, false, false, false, false, false, false, false, false, false}; //프레임의 스트라이크 여부 
		boolean[] is_spare =  {false, false, false, false, false, false, false, false, false, false}; //프레임의 스페어 여부 
		boolean[] is_strike2 =  {false, false, false, false, false, false, false, false, false, false}; //프레임의 스트라이크 여부 
		boolean[] is_spare2 =  {false, false, false, false, false, false, false, false, false, false};	 //프레임의 스페어 여부 
		int fin_score = 0; //최종점수 
		int fin_score2 = 0; //최종점수 		
		
		//시작 lock.
		try {
			start_lock.lock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//콘솔창으로 진행하기 위한 코드.	
		System.out.print("Player 1 : ");
		System.out.print(player);
		System.out.print(" - ");
		if(skill == 3) 
			System.out.print("expert");
		else if(skill == 2) 
			System.out.print("intermediate");	
		else {
			System.out.print("novice");
			skill = 1;
		}
		System.out.print("\n");

		
		System.out.print("Player 2 : ");
		System.out.print(player2);
		System.out.print(" - ");
		if(skill2 == 3) 
			System.out.print("expert");
		else if(skill2 == 2)
			System.out.print("intermediate");
		else {
			System.out.print("novice");
			skill2 = 1;
		}
		System.out.print("\n");
		
		
		for(int i = 1; i<21; i++) { //20회 던지기 위한 for 문 .
			for(int j = 1; j<3; j++) {
				//던질때의 lock.
				try {
					roll_lock.lock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				

				// 플레이어를 번갈아 실행하기위한 for 문. 			
				//홀수번째 던진 공이 strike 일때, 짝수번째 공을 안던지게 하는 조건문 + 20번째 던진 공은 19번째 공이 strike 더라도 던져야함. 
				if(j == 1 && i % 2 == 0 && result == 10 && count != 20) { //플레이어 1
					scoreboardUpper[count - 1] = -1;
					count += 1;
					continue;
				}
				
				if(j == 2 && i % 2 == 0 && result2 == 10 && count2 != 20) { //플레이어 2
					scoreboardUpper2[count2 - 1] = -1;
					count2 += 1;
					continue;
				}
				System.out.println("\n");
				
				//플레이어 1
				if(j == 1) {
					System.out.println("1p throwing!!!");
					
					if(i == 19) {
						System.out.print("19-throwing ");
						result = se.randScore_odd(skill);
					}
					else if(i == 20) {
						System.out.print("20-throwing ");
						result = se.randScore_even(skill, result);
					}
					else if(i % 2 == 1) {
						System.out.print(i + "-throwing ");
						result = se.randScore_odd(skill);
					}
					else {
						System.out.print(i + "-throwing ");
						result = se.randScore_even(skill, result);
					}
					System.out.print(result + ", ");
					
					//count -1 의 인덱스에 scoreboard에 넣어주는거임!! 메서드 scoreCal의 값을!!
					//점수 계산을 할거임!!
					scoreboardUpper[count - 1] = result; 
					SBU[count - 1] = result;

					//여기에 scorecal을 해야함!!!!!!!!!!!!!!!!!!!!!!!  ->strike면 다음 짝수 던지기 skip으로 생각하자.
					scoreboard = se.scoreCal(count, result, scoreboard, scoreboardUpper, is_strike, is_spare);
					SB = scoreboard;
					System.out.print("||");
					for(int d = 0; d<count/2; d++) {
						System.out.print(scoreboard[d]);
					}
					System.out.print("||");
					count += 1;
				}
				
				//플레이어 2
				else if(j == 2) {
					//던질때의 lock.
					try {
						roll_lock2.lock();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("p2 throwing!!");

					if(i == 19) {
						System.out.print("19-throwing ");
						result2 = se.randScore_odd(skill2);
					}
					else if(i == 20) {
						System.out.print("20-throwing ");
						result2 = se.randScore_even(skill2, result2);
					}
					else if(i % 2 == 1) {
						System.out.print(i + "-throwing ");
						result2 = se.randScore_odd(skill2);
					}
					else {
						System.out.print(i + "-throwing ");
						result2 = se.randScore_even(skill2, result2);
					}
					System.out.print(result2 + ", ");
					
					//count -1 의 인덱스에 scoreboard에 넣어준다!! 메서드 scoreCal의 값을!!
					//점수 계산을 할거임!!
					scoreboardUpper2[count2 - 1] = result2; 
					SBU2[count2 - 1] = result2;
					//여기에 scorecal을 해야함!!!!!!!!!!!!!!!!!!!!!!!  ->strike면 다음 짝수 던지기 skip으로 생각하자.
					scoreboard2 = se.scoreCal(count2, result2, scoreboard2, scoreboardUpper2, is_strike2, is_spare2);
					SB2 = scoreboard2;
					System.out.print("||");
					for(int d = 0; d<count2/2; d++) {
						System.out.print(scoreboard2[d]);
						System.out.print(" | ");
					}
					System.out.print("||");
					count2 += 1;
				}
			}
		}
		System.out.print("\n");

		//1~20까지 던졌다. 여기서 19,20이 아무것도 아닐 때 보드에 넣고 끝.  스트라이크 일때, 스페어일때에 한 번 더 하고 끝. 
		
		//10프레임이 오픈일경우는 scorecal에서 이미 완료.
		
		for(int j = 1; j<3; j++) {
			//strike
			if(j == 1) {
				if(count == 21 & scoreboardUpper[18] == 10) {
					System.out.print("21-throwing ");
					result = se.randScore_last(skill, scoreboardUpper[18], scoreboardUpper[19]);
					scoreboardUpper[20] = result;
					SBU[20] = result;
					scoreboard[9] = scoreboard[8] + 10 + scoreboardUpper[19] + result; // 이거 19번째가 스트면 20,21 다 해야
					SB = scoreboard;
				}
				//spare
				else if(count == 21 & scoreboardUpper[18] + scoreboardUpper[19] == 10) {
					System.out.print("21-throwing ");
					result = se.randScore_last(skill,scoreboardUpper[18], scoreboardUpper[19]);
					scoreboardUpper[20] = result;
					SBU[20] = result;
					scoreboard[9] = scoreboard[8] + 10 + result; //
					SB = scoreboard;

				}
		
				System.out.println("\n");
				System.out.println("\n");
				System.out.println("player 1's score: ");
				for(int i = 0; i<21; i++) {
					System.out.print(scoreboardUpper[i] + ", ");
				}
				System.out.print("\n");

				System.out.print("|| ");
				for(int d = 0; d<count/2; d++) {
					System.out.print(scoreboard[d]);
					if(d != count/2 - 1)
						System.out.print(" | ");
				}
				System.out.print(" ||");
				System.out.println("\n");
			}
			
			else if(j == 2) {
				System.out.println("player 2's score: ");
				//frame 10 is strike 
				if(count2 == 21 & scoreboardUpper2[18] == 10) {
					//System.out.print("21-throwing 1");
					//get last score
					result2 = se.randScore_last(skill2, scoreboardUpper2[18], scoreboardUpper2[19]);
					scoreboardUpper2[20] = result2;
					SBU2[20] = result2;
					scoreboard2[9] = scoreboard2[8] + 10 + scoreboardUpper2[19] + result2; // 이거 19번째가 스트면 20,21 다 해야
				}//frame 10 is spare
				else if(count2 == 21 & scoreboardUpper2[18] + scoreboardUpper2[19] == 10) {
					//get last score
					result2 = se.randScore_last(skill2, scoreboardUpper2[18], scoreboardUpper2[19]);
					scoreboardUpper2[20] = result2;
					SBU2[20] = result2;
					scoreboard2[9] = scoreboard2[8] + 10 + result2; 
				}
		
				System.out.print(" ");
				for(int i = 0; i<21; i++) {
					System.out.print(scoreboardUpper2[i] + ", ");
				}
				System.out.print("\n");
				
				System.out.print("|| ");
				for(int d = 0; d<count2/2; d++) {
					System.out.print(scoreboard2[d]);
					if(d == 9)
						break;
					else
						System.out.print(" |  ");
				}
				System.out.print(" ||");
				System.out.println("\n");
		
			}
		}
		if(scoreboardUpper[20] == 0) {
			fin_score = scoreboard[8];
		}
		else {
			fin_score = scoreboard[9];
		}
		
		if(scoreboardUpper2[20] == 0) {
			fin_score2 = scoreboard2[8];
		}
		else {
			fin_score2 = scoreboard2[9];
		}
		//콘솔창에 결과표시를 위한 코드.
		if(fin_score == fin_score2){
			System.out.println("Draw!");
		}
		else if(fin_score > fin_score2) {
			System.out.println("WINNER : Palyer 1");
		}
		else if(fin_score < fin_score2) {
			System.out.println("WINNER : Palyer 2");

		}
	}

}
