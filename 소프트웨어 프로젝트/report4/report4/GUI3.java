/* 화면에 C를 표시하고 이를 클릭하면 다른 랜덤한 위치에 C가 표시되는 프로그램. 
 */
package report4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GUI3 extends JFrame {
	public GUI3() {
		setTitle("클릭 연습용 응용프로그램"); // 제목.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료.
		
		//pane에 올릴 예정, layout manager는 쓰지 않는다. 
		Container container = getContentPane();
		container.setLayout(null);
		JLabel label = new JLabel("C");
		  
		label.setLocation(100, 100); // 기본위치. 
		label.setSize(10, 10);
		container.add(label); //pane에 label을 올린다. 
		
		//MouseListener를 사용해야함.
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { //클릭이 발생하면.
				int xmin = 0, xmax = 250, ymin = 0, ymax = 250; // 랜덤한 위치로 나타내기위한 범위.
				int xpos = xmin + (int)(Math.random() * ((xmax - xmin) + 1));
				int ypos = ymin + (int)(Math.random() * ((ymax - ymin) + 1));
				label.setLocation(xpos, ypos);
			}

			@Override
			public void mousePressed(MouseEvent e) { //필요 없음.
				
			}

			@Override
			public void mouseReleased(MouseEvent e) { //필요 없음.
				
			}

			@Override
			public void mouseEntered(MouseEvent e) { //필요 없음.
				
			}

			@Override
			public void mouseExited(MouseEvent e) { //필요 없음.
	
			}	
		});
		setSize(300, 300);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI3();
	}
}
