/*
 * 메인함수에서는 GUI를 실행하고 Modify의 main함수를 실행한다. 
 * BorderLayout을 사용하여 North, South, Center, East, West에 각각 panel을 두었다. 
 * 상단은 플레이어의 정보를 입력 받는데 사용된다.
 * 하단은 경기를 진행하기 전 내기를 정하여 경기 이후에 이를 이용하여 벌칙이나 상금을 받을 수 있다. 
 * 좌측은 게임을 위한 가이드, 우측은 공을 던지는 버튼을 두었다. 
 * 그리고 가운데를 점수 및 코멘트를 표시하는 칸으로 두었다.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Game extends JFrame {
	//Final variable for Brown color 
	public static final Color BROWN = new Color(153, 102, 0);
	
	Container container = getContentPane(); //pane
	
	//Counter for player1, 2 each.
	public static int counter = 0;
	public static int counter2 = 0;
	public static int winner = -1;
	
	//Button for rolling (right side)
	JButton player_roll = new JButton("Player 1 ROLL!");
	JButton player_roll2 = new JButton("Player 2 ROLL!");
	
	//Header and content for JTable (two tables)
	String header[] = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
	String upper[][] = {
			{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
			{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
	};
	String lower[][] = {
			{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
			{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}
	};
	
	//Set defaultTableModels
	DefaultTableModel defaultTableModel = new DefaultTableModel(upper, header);
	DefaultTableModel defaultTableModel2 = new DefaultTableModel(lower, header);

	//Set JTables & mention for cen.
	JTable table = new JTable(defaultTableModel);
	JTable table2 = new JTable(defaultTableModel2);
	static JLabel mention = new JLabel("I LOVE BOWLING", JLabel.CENTER);

	public Game() {
		//skill level for comboBox.
		String[] skill={"novice", "intermediate", "expert"}; 
		
		//title.
		setTitle("Bowling Game"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close setting 
				
		//panels.
		JPanel top = new JPanel(); 
		JPanel cen = new JPanel();
		JPanel bot = new JPanel();
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		cen.setLayout(new GridLayout(3,1)); //GridLayout 
		right.setLayout(new GridLayout(4,1)); //GridLayout 
		left.setLayout(new GridLayout(9,1)); //GridLayout
		
		//panel color setting.
		top.setOpaque(true);
		top.setBackground(Color.LIGHT_GRAY);
		bot.setOpaque(true);
		bot.setBackground(Color.LIGHT_GRAY);
		right.setOpaque(true);
		right.setBackground(BROWN);
		left.setOpaque(true);
		left.setBackground(Color.WHITE);

		//Make components for top panel.
		JLabel playerName = new JLabel("player 1 : ");
		JLabel playerName2 = new JLabel("player 2 : ");
		final JTextField name = new JTextField(10);
		final JTextField name2 = new JTextField(10);
		JComboBox box = new JComboBox(skill);
		JComboBox box2 = new JComboBox(skill);
		JButton enterButton = new JButton("Enter");
		JButton enterButton2 = new JButton("Enter");
		
		//Get info from the box.
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = box.getSelectedItem().toString();
				if(temp.equals("novice"))
					Modify.skill = 1;
				else if(temp.equals("intermediate"))
					Modify.skill = 2;
				else
					Modify.skill = 3;
			}
		});
		
		//Get info from the box2.
		box2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = box2.getSelectedItem().toString();
				if(temp.equals("novice"))
					Modify.skill2 = 1;
				else if(temp.equals("intermediate"))
					Modify.skill2 = 2;
				else
					Modify.skill2 = 3;
			}
		});
		
		//Name update.
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Modify.player = name.getText();
			}
		});
		
		//Name update.
		enterButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Modify.player2 = name2.getText();
				Modify.start_lock.unlock();
				player_roll.setVisible(true);
			}
		});
		
		//Add components for top panel.
		top.add(playerName);
		top.add(name);
		top.add(box);
		top.add(enterButton);
		top.add(playerName2);
		top.add(name2);
		top.add(box2);
		top.add(enterButton2);

		//Make components for cen panel.
		table.setTableHeader(null);
		mention.setFont(new Font("Serif", Font.BOLD, 18));

		//Add components for cen panel.
		//cen.add(tableName);
		cen.add(table);
		cen.add(mention);
		cen.add(table2);
		
		//Make components for bot panel.
		JLabel betLabel = new JLabel("[ Playing bowling == Betting something!! ] --> Let's bet!! ");
		JTextField bet = new JTextField(30);
		JButton fixButton = new JButton("FIX!");
		
		//Fix bet.
		fixButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bet.setEditable(false);
			}
		});
		
		//Add components for bot panel.
		bot.add(betLabel);
		bot.add(bet);
		bot.add(fixButton);
		
		//Make components for right panel.
		MyMouseListener listener = new MyMouseListener();
		MyMouseListener2 listener2 = new MyMouseListener2();

		//Set button for rolling.
		player_roll.setVisible(false);
		player_roll2.setVisible(false);
		player_roll.setSize(100, 100);
		player_roll2.setSize(100, 100);
		player_roll.addMouseListener(listener);
		player_roll2.addMouseListener(listener2);

		//Add components for right panel.
		right.add(new JLabel());
		right.add(player_roll);
		right.add(player_roll2);

		//Make components for left panel. (how to play)
		JLabel howToPlay = new JLabel("HOW TO PLAY", JLabel.CENTER);
		JLabel howToPlay2 = new JLabel("1: Type player's name ", JLabel.CENTER);
		JLabel howToPlay2_1 = new JLabel("and choose the skill level ", JLabel.CENTER);
		JLabel howToPlay2_2 = new JLabel("and press enter!", JLabel.CENTER);
		JLabel howToPlay2_3 = new JLabel("Using bottom text box, bet something and fix it!", JLabel.CENTER);
		JLabel howToPlay3 = new JLabel("2: Press ROLL button in order!", JLabel.CENTER);
		JLabel howToPlay3_1 = new JLabel("Player 1 first.", JLabel.CENTER);
		JLabel howToPlay3_2 = new JLabel("Press the button even you've got \"STRIKE.\"", JLabel.CENTER);
		JLabel howToPlay4 = new JLabel("3: Enjoy The Bowling Game!", JLabel.CENTER);

		//Make components for left panel.
		left.add(howToPlay);
		left.add(howToPlay2);
		left.add(howToPlay2_1);
		left.add(howToPlay2_2);
		left.add(howToPlay2_3);
		left.add(howToPlay3);
		left.add(howToPlay3_1);
		left.add(howToPlay3_2);
		left.add(howToPlay4);

		//Put panels on container.
		container.setLayout(new BorderLayout(0, 0));		
		container.add(top, BorderLayout.NORTH);
		container.add(cen, BorderLayout.CENTER);
		container.add(bot, BorderLayout.SOUTH); 
		container.add(right, BorderLayout.EAST);
		container.add(left, BorderLayout.WEST);

		setSize(1200, 350); //Frame size. 
		setVisible(true);
	}
	
	//MouseListener for player_roll.
	class MyMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			//Modify에 걸려있는 lock을 푼다.
			Modify.roll_lock.unlock();
			counter++;
		}
		@Override
		public void mouseEntered(MouseEvent e) {			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			JButton b1 = (JButton)e.getSource();
			player_roll2.setVisible(true);
			
			//Get info from SBU(scoreboardUpper) and update JTable table.
			String tempU = Integer.toString(Modify.SBU[counter - 1]);
			table.setValueAt(tempU, 0, counter - 1);
			
			//Set mention for center mention JLabel. 
			if(counter % 2 == 0 && Modify.SBU[counter - 2] == 10) {
				String temp_mention = Modify.player + "'s last rolling was STRIKE!!";
				mention.setText(temp_mention);
				mention.repaint();
			}
			else if(counter % 2 == 1 && Modify.SBU[counter - 1] == 10){
				String temp_mention = Modify.player + " got STRIKE!!! Please don't skip to clicking the button on your next turn :)";
				mention.setText(temp_mention);
				mention.repaint();
			}
			else if(counter % 2 == 0 && !(Modify.SBU[counter - 2] == 10) && (Modify.SBU[counter - 2] + Modify.SBU[counter - 1]) == 10 ) {
				String temp_mention = Modify.player + " got " + Modify.SBU[counter - 1] + " score!! and SPARE for this frame ;)";
				mention.setText(temp_mention);
				mention.repaint();

			}
			else {
				String temp_mention = Modify.player + " got " + Modify.SBU[counter - 1] + " score!!";
				mention.setText(temp_mention);
				mention.repaint();
			}

			//Get info from SB(scoreboard) and update JTable table.
			if(counter == 21) {
				String temp = Integer.toString(Modify.SB[counter/2 - 1]);
				table.setValueAt(temp, 1, counter - 2);
			}
			else {
				if(counter % 2 == 1 && counter >= 5) {
					String temp = Integer.toString(Modify.SB[counter/2]);
					String tempL = Integer.toString(Modify.SB[counter/2 - 1 ]);
					String tempLL = Integer.toString(Modify.SB[counter/2 - 2 ]);

					table.setValueAt(temp, 1, counter);
					table.setValueAt(tempL, 1, counter - 2);
					table.setValueAt(tempLL, 1, counter - 4);

				}
				else if(counter % 2 == 1 && counter == 3){
					String temp = Integer.toString(Modify.SB[counter/2]);
					String tempL = Integer.toString(Modify.SB[counter/2 - 1]);
					table.setValueAt(temp, 1, counter);
					table.setValueAt(tempL, 1, counter - 2);
				}
				else if(counter % 2 == 1){
					String temp = Integer.toString(Modify.SB[counter/2]);
					table.setValueAt(temp, 1, counter);
				}
				
				if(counter % 2 == 0 && counter >= 6){
					String temp = Integer.toString(Modify.SB[counter/2 - 1]);
					String tempL = Integer.toString(Modify.SB[counter/2 - 2]);
					String tempLL = Integer.toString(Modify.SB[counter/2 - 3]);

					table.setValueAt(temp, 1, counter - 1);
					table.setValueAt(tempL, 1, counter - 3);
					table.setValueAt(tempLL, 1, counter - 5);
				}
				else if(counter % 2 == 0 && counter == 4){
					String temp = Integer.toString(Modify.SB[counter/2 - 1]);
					String tempL = Integer.toString(Modify.SB[counter/2 - 2 ]);
					table.setValueAt(temp, 1, counter - 1);
					table.setValueAt(tempL, 1, counter - 3);
				}
				else if(counter % 2 == 0){
					String temp = Integer.toString(Modify.SB[counter/2 - 1]);
					table.setValueAt(temp, 1, counter - 1);
				}
			}
			//Update JTable.
			defaultTableModel.fireTableDataChanged();
			player_roll.setVisible(false);
		}
		@Override
		public void mouseExited(MouseEvent e) {
						
		}
		
	}
	
	//MouseListener2 for player_roll2.
	class MyMouseListener2 implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			//Modify에 걸려있는 lock을 푼다.
			Modify.roll_lock.unlock();
			Modify.roll_lock2.unlock();
			counter2++;
		}
		@Override
		public void mouseEntered(MouseEvent e) {			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			player_roll.setVisible(true);
			JButton b1 = (JButton)e.getSource();
			
			//get info from SBU2(scoreboardUpper2) and update JTable table2.
			String tempU = Integer.toString(Modify.SBU2[counter2 - 1]);
			table2.setValueAt(tempU, 0, counter2 - 1);
			
			//Set mention for center mention JLabel. 
			if(counter2 % 2 == 0 && Modify.SBU2[counter2 - 2] == 10) {
				String temp_mention2 = Modify.player2 + "'s last rolling was STRIKE!!";
				mention.setText(temp_mention2);
				mention.repaint();
			}
			else if(counter2 % 2 == 1 && Modify.SBU2[counter2 - 1] == 10){
				String temp_mention2 = Modify.player2 + " got STRIKE!!! Please don't skip to clicking the button on your next turn :)";
				mention.setText(temp_mention2);
				mention.repaint();
			}
			else if(counter2 % 2 == 0 && !(Modify.SBU2[counter2 - 2] == 10) && (Modify.SBU2[counter2 - 2] + Modify.SBU2[counter2 - 1]) == 10 ) {
				String temp_mention2 = Modify.player2 + " got " + Modify.SBU2[counter2 - 1] + " score!! and SPARE for this frame ;)";
				mention.setText(temp_mention2);
				mention.repaint();
			}
			else {
				String temp_mention2 = Modify.player2 + " got " + Modify.SBU2[counter2 - 1] + " score!!";
				mention.setText(temp_mention2);
				mention.repaint();
			}
			
			//Get info from SB2(scoreboard2) and update JTable table2.
			if(counter2 == 21) {
				String temp = Integer.toString(Modify.SB2[counter2/2 - 1]);
				table2.setValueAt(temp, 1, counter2 - 2);
				//Set winner
				if(counter2 == 21)
					counter2++;	
					if(Modify.SB[9] == Modify.SB2[9]) {
						winner = 0;
					}
					else if(Modify.SB[9] > Modify.SB2[9]) {
						winner = 1;
					}
					else {
						winner = 2;
					}
				//Show winner.
				if(counter2 == 22) {
					if(winner == 0) {
						mention.setText("DRAW");
						mention.repaint();
					}
					else if(winner == 1) {
						String tempResult = Modify.player + " is WINNER!!!!!";
						mention.setText(tempResult);
						mention.repaint();

					}
					else {
						String tempResult = Modify.player2 + " is WINNER!!!!!";
						mention.setText(tempResult);
						mention.repaint();
					}
					//Set Invisible to rolling buttons.
					player_roll.setVisible(false);
					player_roll2.setVisible(false);
						
				}
			}
			else {
				if(counter2 % 2 == 1 && counter2 >= 5) {
					String temp = Integer.toString(Modify.SB2[counter2/2]);
					String tempL = Integer.toString(Modify.SB2[counter2/2 - 1 ]);
					String tempLL = Integer.toString(Modify.SB2[counter2/2 - 2 ]);

					table2.setValueAt(temp, 1, counter2);
					table2.setValueAt(tempL, 1, counter2 - 2);
					table2.setValueAt(tempLL, 1, counter2 - 4);

				}
				else if(counter2 % 2 == 1 && counter2 == 3){
					String temp = Integer.toString(Modify.SB2[counter2/2]);
					String tempL = Integer.toString(Modify.SB2[counter2/2 - 1]);
					table2.setValueAt(temp, 1, counter2);
					table2.setValueAt(tempL, 1, counter2 - 2);
				}
				else if(counter2 % 2 == 1){
					String temp = Integer.toString(Modify.SB2[counter2/2]);
					table2.setValueAt(temp, 1, counter2);
				}
				
				if(counter2 % 2 == 0 && counter2 >= 6){
					String temp = Integer.toString(Modify.SB2[counter2/2 - 1]);
					String tempL = Integer.toString(Modify.SB2[counter2/2 - 2]);
					String tempLL = Integer.toString(Modify.SB2[counter2/2 - 3]);

					table2.setValueAt(temp, 1, counter2 - 1);
					table2.setValueAt(tempL, 1, counter2 - 3);
					table2.setValueAt(tempLL, 1, counter2 - 5);
				}
				else if(counter2 % 2 == 0 && counter2 == 4){
					String temp = Integer.toString(Modify.SB2[counter2/2 - 1]);
					String tempL = Integer.toString(Modify.SB2[counter2/2 - 2 ]);
					table2.setValueAt(temp, 1, counter2 - 1);
					table2.setValueAt(tempL, 1, counter2 - 3);
				}
				else if(counter2 % 2 == 0){
					String temp = Integer.toString(Modify.SB2[counter2/2 - 1]);
					table2.setValueAt(temp, 1, counter2 - 1);
				}
			}
			//Update JTable.
			defaultTableModel.fireTableDataChanged();
			player_roll2.setVisible(false);
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	//For getting key input.
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			char keyChar = e.getKeyChar();
			writeName(keyChar);
		}
	}
	
	//For TextField name.
	private void writeName(char c) {
		String add = String.valueOf(c);
		Modify.player = Modify.player.concat(add);
	}
	
	//For TextField name2.	
	private void writeName2(char c) {
		String add = String.valueOf(c);
		Modify.player2 = Modify.player2.concat(add);
	}
	
	public static void main(String[] args) {
		//Start GUI.
		new Game();
		//Start class Modify().
		Modify se = new Modify();
		se.main(args);
	}

}
