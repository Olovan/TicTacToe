import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

//TopMenuBar contains the drop-down menus at the top
public class TopMenuBar extends JMenuBar
{
	Board board;
	Settings settings;
	
	AppearanceWindow appearanceWindow = null;

	JMenu options;
	JMenu difficulty;

	JCheckBoxMenuItem computerFirst;
	JMenuItem restartGame;
	JMenuItem resetScore;

	//Difficulty Levels
	JRadioButtonMenuItem brainDead; 
	JRadioButtonMenuItem easy;
	JRadioButtonMenuItem medium;
	JRadioButtonMenuItem unbeatable;

	JButton appearance;
	JButton scoreBoard;

	public TopMenuBar(Board board)
	{
		this.board = board;
		settings = board.settings;

		//Instantiate Components
		options = new JMenu("Options");
		difficulty = new JMenu("Difficulty");
		brainDead = new JRadioButtonMenuItem("Brain Dead", settings.AI_DIFFICULTY == 0);
		easy = new JRadioButtonMenuItem("Easy", settings.AI_DIFFICULTY == 1);
		medium = new JRadioButtonMenuItem("Medium", settings.AI_DIFFICULTY == 3);
		unbeatable = new JRadioButtonMenuItem("Unbeatable", settings.AI_DIFFICULTY == 6);
		computerFirst = new JCheckBoxMenuItem("AI Goes First", true);
		restartGame = new JMenuItem("Restart Game");
		resetScore = new JMenuItem("Reset Score");
		appearance = new JButton("Appearance");
		scoreBoard = new JButton("Score Board");

		//Component Settings
		options.setMnemonic(KeyEvent.VK_O);
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(brainDead);
		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(unbeatable);
		computerFirst.setState(settings.aiMoveFirst);
		appearance.setBorderPainted(false);
		scoreBoard.setBorderPainted(false);
		appearance.setFocusPainted(false);
		scoreBoard.setFocusPainted(false);
		appearance.setBackground(new Color(0, 0, 0, 0));
		scoreBoard.setBackground(new Color(0, 0, 0, 0));

		//Add Listeners
		brainDead.addActionListener(new DiffButtonListener(0));
		easy.addActionListener(new DiffButtonListener(1));
		medium.addActionListener(new DiffButtonListener(3));
		unbeatable.addActionListener(new DiffButtonListener(6));
		computerFirst.addActionListener(new TopBarButtonListener());
		appearance.addActionListener(new TopBarButtonListener());
		scoreBoard.addActionListener(new TopBarButtonListener());
		resetScore.addActionListener(new TopBarButtonListener());
		restartGame.addActionListener(new TopBarButtonListener());

		//Add components
		difficulty.add(brainDead);
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(unbeatable);
		options.add(computerFirst);
		options.add(difficulty);
		options.addSeparator();
		options.add(resetScore);
		options.addSeparator();
		options.add(restartGame);

		add(options);
		add(Box.createHorizontalStrut(10));
		add(appearance);
		add(Box.createHorizontalGlue());
		add(scoreBoard);
	}

	class DiffButtonListener implements ActionListener
	{
		int level = 0;
		public DiffButtonListener(int level)
		{
			this.level = level;
		}

		public void actionPerformed(ActionEvent e)
		{
			settings.AI_DIFFICULTY = level;
		}
	}
	
	private class TopBarButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			switch(e.getActionCommand())
			{
			case "Restart Game":
				GameLogic.restartGame();
				break;

			case "Reset Score":
				settings.aiScore = 0;
				settings.playerScore = 0;
				settings.ties = 0;
				break;

			case "Score Board":
				JOptionPane.showMessageDialog(board, "Player Score: " + settings.playerScore + "\nComputer Score: " + settings.aiScore + "\nTies: " + settings.ties, "Score Board", JOptionPane.INFORMATION_MESSAGE);
				repaint();
				break;

			case "Appearance":
				if(appearanceWindow == null)
				{
					appearanceWindow = new AppearanceWindow(board);
				}
				else
				{
					appearanceWindow.setLocation(board.getLocation().x + 100, board.getLocation().y + 100);
					appearanceWindow.setVisible(true);
				}
				repaint();
				break;
			case "AI Goes First":
				settings.aiMoveFirst = !settings.aiMoveFirst;
				computerFirst.setState(settings.aiMoveFirst);
				break;
			}
		}
	}
}
