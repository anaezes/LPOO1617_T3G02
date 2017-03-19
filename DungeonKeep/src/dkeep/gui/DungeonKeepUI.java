package dkeep.gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import dkeep.logic.EnumGuardType;
import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Vilan.EnumVillainType;
import dkeep.logic.Game.EnumGameState;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;


public class DungeonKeepUI{

	private JFrame frame;
	private JLabel gameStatusLabel;
	private Game game;
	private JPanel gamePanel;
	private JPanel[][] gameBoard;
	private KeyListener keyListener;
	private GameOptions gameOptions;
	
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
		}
				);
	}

	/**
	 * Create the application.
	 */
	public DungeonKeepUI() {
		keyListener = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(game.getGameState() != EnumGameState.Running)
					return;
				
				boolean moved = false;
				int key = e.getKeyCode();
				switch(key) {
				case KeyEvent.VK_LEFT: 
					game.moveHero(EnumMoves.LEFT);
					moved = true;
					break;
				case KeyEvent.VK_RIGHT: 
					game.moveHero(EnumMoves.RIGHT);
					moved = true;
					break;
				case KeyEvent.VK_UP:
					game.moveHero(EnumMoves.UP);
					moved = true;
					break;
				case KeyEvent.VK_DOWN: 
					game.moveHero(EnumMoves.DOWN);
					moved = true;
				}
		
				if(moved) {
					updateGraphics();
					gamePanel.repaint();
					validateGameRunning();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		};
		
		initialize();
	}

	public void newGame() {
		int[] guard_y = new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
		int[] guard_x = new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};

		char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','h',' ',' ','I', ' ', 'X', ' ', 'G', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', 'O', '*', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};

		int numOfOgres = gameOptions.getNumberOfOgres();
		EnumGuardType guardType = gameOptions.getGuardType();

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardOne);
		GameMap gameMap2 = new GameMap(BoardTwo);
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);

		Game newGame = new Game(gameMaps, guardType, numOfOgres);
		newGame.setGuardPath(guard_y, guard_x);
		this.game = newGame;

		setGameStatusLabelText("Prepare to figth!!! Click on buttons to move hero!");
	}

	private void initGraphics() {
		int aux = game.getBoard().getBoardSize();
		this.gameBoard = new JPanel[aux][aux];

		char character;
		for(int i = 0; i < aux ; i++ )
			for(int j = 0; j < aux; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();

				else if(game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

				else if(game.getHeroClub().getXCoordinate() == i && game.getHeroClub().getYCoordinate()  == j) 
					character = game.getHeroClub().getCharacter();

				if(game.getExitDoors().size() != 0) {
					for(int k = 0; k < game.getExitDoors().size(); k++)
						if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j)
							character = game.getExitDoors().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0) {
					for(int k = 0; k < game.getVilans().size(); k++) {
						if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j)
							character = game.getVilans().get(k).getCharacter();
					}
				}

				gameBoard[i][j] = new GameObject(gamePanel.getWidth()/aux, gamePanel.getHeight()/aux, character);
				gamePanel.add(gameBoard[i][j]);	
			}
	}

	private void updateGraphics() {

		int aux = game.getBoard().getBoardSize();

		char character;
		for(int i = 0; i < aux ; i++ )
			for(int j = 0; j < aux; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(character == 'X' && character == 'I')
					continue;

				else if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();

				else if(game.getLever() != null && game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

				else if(game.getKey() != null && game.getKey().getXCoordinate() == i && game.getKey().getYCoordinate() == j) 
					character = game.getKey().getCharacter();

				else if(game.getHeroClub().getXCoordinate() == i && game.getHeroClub().getYCoordinate()  == j) 
					character = game.getHeroClub().getCharacter();

				else if(game.getExitDoors().size() != 0) {
					for(int k = 0; k < game.getExitDoors().size(); k++)
						if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j)
							character = game.getExitDoors().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0) {
					for(int k = 0; k < game.getVilans().size(); k++)
						if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j)
							character = game.getVilans().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0 && game.getVilans().get(0).getType() == EnumVillainType.Ogre) {
					for(int k = 0; k < game.getVilans().size(); k++)
						if(game.getVilans().get(k).getClub().getXCoordinate() == i && game.getVilans().get(k).getClub().getYCoordinate() == j)
							character = '*';
				}

				((GameObject)gameBoard[i][j]).switchType(character);
			}
	}



	private void setGameStatusLabelText(String text) {
		gameStatusLabel.setText(text);
	}

	private void validateGameRunning() {
		if(game.getGameState() == EnumGameState.Win)
			setGameStatusLabelText("YOU WIN!");
		
		else if(game.getGameState() == EnumGameState.Lost)
			setGameStatusLabelText("Game Over!");
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(Color.GRAY);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 670, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		gameOptions = new GameOptions(frame);
		gameOptions.setVisible(false);

		gameStatusLabel = new JLabel("MessageStatus");
		gameStatusLabel.setBounds(12, 666, 374, 15);
		frame.getContentPane().add(gameStatusLabel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Dialog", Font.BOLD, 15));
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmNewGame = new JMenuItem("        New Game");
		mntmNewGame.setHorizontalAlignment(SwingConstants.CENTER);
		mntmNewGame.setFont(new Font("Dialog", Font.BOLD, 14));
		menuBar.add(mntmNewGame);
		mntmNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
				gamePanel = new JPanel();
				gamePanel.setLayout(new GridLayout(game.getBoard().getBoardSize(), game.getBoard().getBoardSize()));
				gamePanel.setBounds(30, 30, 600, 600);
				gamePanel.setBackground(Color.WHITE);
				gamePanel.setFocusable(true);
				gamePanel.addKeyListener(keyListener);
			
				frame.getContentPane().add(gamePanel);
				gamePanel.requestFocus();
				initGraphics();
			}
		});
		
		JMenuItem mntmOptions = new JMenuItem("        Options");
		mntmOptions.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mntmOptions);
		mntmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOptions.setVisible(true);
			}
		});
	
		
		JMenuItem mntmEditMap = new JMenuItem("        Edit Map");
		mntmEditMap.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mntmEditMap);
		
		JMenuItem mntmExit = new JMenuItem("        Exit");
		mntmExit.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		setGameStatusLabelText("Please click New Game to start!");
	}
}
