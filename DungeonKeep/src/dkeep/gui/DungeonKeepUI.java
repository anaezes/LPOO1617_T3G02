package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import ckeep.cli.Print;
import dkeep.logic.EnumGuardType;
import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.GameMap;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;


public class DungeonKeepUI {
	
	private JFrame frame;
	private JTextField numberOfOgres;
	private JComboBox<EnumGuardType> guardsCombo;
	private JTextArea gameBoard;
	private JButton DownBtn;
	private JButton RightBtn;
	private JButton LeftBtn;
	private JButton UpBtn;
	
	private Game game;
	private Print printBoard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonKeepUI window = new DungeonKeepUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DungeonKeepUI() {
		initialize();
	}
	
	public void newGame() {
		int[] guard_y = new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
		int[] guard_x = new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};

		char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','h',' ',' ','I', ' ', 'X', ' ', 'G', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'X',' ','X',' ','I', ' ', 'X', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};
		
		int numOfOgres = numberOfOgres.getText().hashCode();
		String guardType = guardsCombo.getSelectedItem().toString();
		System.out.println(guardType);
		
		switch(numOfOgres) {
		case 0: //in case 1 ogre
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			break;
		case 1: // in case 2 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			break;
		case 2: // in case 3 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			BoardTwo[6][5] = 'O';
			BoardTwo[6][6] = '*';
			break;	
		}

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardOne);
		GameMap gameMap2 = new GameMap(BoardTwo);
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);
		
		Game newGame = new Game(gameMaps);
		newGame.setGuardPath(guard_y, guard_x);
		this.game = newGame;
		this.printBoard = new Print();
		setMovementButtons(true);
		printMap(this.game);
	}
	
	private void printMap(Game game) {
		gameBoard.setText(printBoard.printBoard(game));
	}
	
	private void setMovementButtons(boolean stateBtn) {
		UpBtn.setEnabled(stateBtn);
		RightBtn.setEnabled(stateBtn);
		DownBtn.setEnabled(stateBtn);
		LeftBtn.setEnabled(stateBtn);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 627, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(12, 34, 135, 15);
		frame.getContentPane().add(lblNumberOfOgres);
		
		numberOfOgres = new JTextField();
		numberOfOgres.setBounds(157, 32, 49, 19);
		frame.getContentPane().add(numberOfOgres);
		numberOfOgres.setColumns(10);
		
		JLabel lblGuardPersonality = new JLabel("Guard personality");
		lblGuardPersonality.setBounds(12, 59, 150, 15);
		frame.getContentPane().add(lblGuardPersonality);
		
		guardsCombo = new JComboBox<EnumGuardType>();
		guardsCombo.setModel(new DefaultComboBoxModel<EnumGuardType>(EnumGuardType.values()));
		guardsCombo.setBounds(157, 59, 129, 20);
		frame.getContentPane().add(guardsCombo);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		btnNewGame.setBounds(441, 54, 115, 25);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(441, 340, 115, 25);
		frame.getContentPane().add(btnExit);
		
		gameBoard = new JTextArea();
		gameBoard.setFont(new Font("Courier 10 Pitch", Font.BOLD, 12));
		gameBoard.setEditable(false);
		gameBoard.setBounds(12, 86, 374, 279);
		frame.getContentPane().add(gameBoard);
		
		UpBtn = new JButton("UP");
		UpBtn.setEnabled(false);
		UpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.UP);
				printMap(game);
			}
		});
		UpBtn.setBounds(460, 140, 80, 20);
		frame.getContentPane().add(UpBtn);
		
		DownBtn = new JButton("DOWN");
		DownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.DOWN);
				printMap(game);
			}
		});
		DownBtn.setEnabled(false);
		DownBtn.setBounds(460, 235, 80, 20);
		frame.getContentPane().add(DownBtn);
		
		RightBtn = new JButton("RIGHT");
		RightBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.RIGHT);
				printMap(game);
			}
		});
		RightBtn.setEnabled(false);
		RightBtn.setBounds(514, 191, 80, 20);
		frame.getContentPane().add(RightBtn);
		
		LeftBtn = new JButton("LEFT");
		LeftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.LEFT);
				printMap(game);
			}
		});
		LeftBtn.setEnabled(false);
		LeftBtn.setBounds(414, 191, 80, 20);
		frame.getContentPane().add(LeftBtn);
		
		JLabel gameStatusLabel = new JLabel("MessageStatus");
		gameStatusLabel.setBounds(12, 381, 70, 15);
		frame.getContentPane().add(gameStatusLabel);
	}
}