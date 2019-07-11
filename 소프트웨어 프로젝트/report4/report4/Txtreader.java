/*1사용자로부터 입력과 출력 파일명을 입력 받고, 입력 파일(input.txt)에 들어 있는 텍스트의 문자수, 단어수, 문장수를 count하여 출력하는 프로그램. 
 * 출력결과는 출력 파일에 저장되도록 한다. (output.txt)
 * 문자 : 공백이 아닌 모든 문자
 * 단어 : 알파벳이나 숫자로 이루어진 연속적인 문자
 * 문장 :  세가지 문자(.  ?  ! )로 끝나면 하나의 문장이다. 
 */
package report4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class Txtreader {
	public static void main(String[] args) {
		int [] count = new int[3]; // 문자, 단어, 문장
		
		try {
			File file = new File("./input.txt"); //input file
			FileReader filereader = new FileReader(file);
			FileWriter fWriter = null;
			fWriter = new FileWriter("output.txt"); // result save file
			int singleCh = 0; //return by int
			
			while((singleCh = filereader.read()) != -1) {
				System.out.print((char)singleCh);
				if((char)singleCh != ' ' && (char)singleCh != '\n' && (char)singleCh != '\r') //공백문자, 개행문자가 아니면 문자.
					count[0]++;
				if((char)singleCh == ' ' || (char)singleCh == '\r') //단어는 공백문자와 개행문자를 사이에 두기에 이를 계산하면 단어의 개수가 나온다. 
					count[1]++;
				if((char)singleCh == '.' || (char)singleCh == '?' || (char)singleCh == '!') //문장은 . ? !로 끝나기에 이를 계산하면 문장의 개수가 나온다.
					count[2]++;
			}
			//출력! 
			for(int i = 0; i<3; i++)
				fWriter.write(count[i]+ "\r\n");
			fWriter.close(); // 이걸 닫아야 output.txt에 적힌다!!
		}catch (FileNotFoundException e) {
		}catch(IOException e) {
			System.out.println(e);
		}
		
		System.out.println("\n");
		for(int i = 0; i<3; i++)
			System.out.println(count[i]);
	}

}
