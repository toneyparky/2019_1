/* GUI를 이용하여 프로그램 만들기.
 * 상단에 버튼 3개, 하단에 label과 입력창, 중간에는 열개의 *이 랜덤하게 배치.
 */
package report4;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GUI2 extends JFrame {
	public GUI2() {
		setTitle("여러 개의 패널을 가진 프레임"); //제목 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기 설정 
		
		Container container = getContentPane(); //pane

		//판넬 3개를 만든다.
		JPanel top = new JPanel(); 
		JPanel cen = new JPanel();
		JPanel bot = new JPanel();
		cen.setLayout(null); //가운데는 layout manager 안쓰기 
		JLabel[] labels = new JLabel[10]; //*을 10개 담을 배열 
		
		//판넬의 색 설정.
		top.setOpaque(true);
		top.setBackground(Color.LIGHT_GRAY);
		bot.setOpaque(true);
		bot.setBackground(Color.YELLOW);
		
		//상단 판넬의 버튼, 이름설정.
		top.add(new JButton("열기"));
		top.add(new JButton("닫기"));
		top.add(new JButton("나가기"));
		
		//랜덤하게 *를 표기하기위해 위치를 나타낼 범위를 위한 변수들. 
		int xmin = 0, xmax = 290, ymin = 0, ymax = 200;
		
		//각 *마다 랜덤한 위치를 설정, 샐깔은 빨간색.
		for(int i = 0; i<10; i++) {
			int xpos = xmin + (int)(Math.random() * ((xmax - xmin) + 1));
			int ypos = ymin + (int)(Math.random() * ((ymax - ymin) + 1));
			System.out.print(xpos);
			System.out.print("  ");
			System.out.println(ypos);
			labels[i] = new JLabel("*");
			labels[i].setLocation(xpos, ypos);
			labels[i].setSize(10, 10);
			labels[i].setForeground (Color.red);
			cen.add(labels[i]);
		}
	
		//하단 판넬의 버튼과 텍스트필드.
		bot.add(new JButton("Word Input"));
		bot.add(new JTextField("                     "));

		//판넬을 pane에 붙여준다.
		container.setLayout(new BorderLayout(0, 0));		
		container.add(top, BorderLayout.NORTH);
		container.add(cen, BorderLayout.CENTER);
		container.add(bot, BorderLayout.SOUTH);
		
		setSize(300, 300); //크기. 
		setVisible(true);
	}

	public static void main(String[] args) {
		new GUI2(); //실행.
	}
}
