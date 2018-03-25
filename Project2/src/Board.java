import javax.swing.*;


import java.awt.*;


//Board class holds the Tic Tac Toe board and handles the visual elements of the Board
class Board extends JFrame
{
	public Settings settings;
	public Tile tiles[] = new Tile[9];
	JPanel gridPanel;

	public static void main (String[] args) {
		new Board();
	}

	public Board()
	{
		//Load Settings
		Settings temp = null;
		if((temp = Settings.importFromFile("./data/settings.db")) != null)
		{
			settings = temp;
		}
		else
		{
			System.out.println("Falling Back to Default Settings");
			settings = new Settings();
		}
		
		//Set up Window
		setMinimumSize(settings.WINDOW_SIZE);
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gridPanel = new JPanel();

		gridPanel.setBackground(settings.BACKGROUND_COLOR);
		GridLayout gridLayout = new GridLayout(3, 3);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		gridPanel.setLayout(gridLayout);

		//Add Tiles to gridPanel
		for (int i = 0; i < 9; i++) 
		{
			Tile currentTile = new Tile(i, this);
			tiles[i] = currentTile;
			GameLogic.textBoard[i] = '\0';
			gridPanel.add(currentTile);
		}
		
		add(new TopMenuBar(this), BorderLayout.NORTH);
		add(gridPanel);

		setVisible(true);

		//Start Game
		GameLogic.setCurrentBoard(this);
		GameLogic.restartGame();
	}

	public void setTileBackground(int index, Color color)
	{
		//Sets the overlay variable so that the win/lose highlights will persist
		//even if you change the highlight colors or other settings
		if(color.equals(settings.WIN_HIGHLIGHT))
			tiles[index].overlay = Tile.HIGHLIGHT.WIN;
		if(color.equals(settings.LOSE_HIGHLIGHT))
			tiles[index].overlay = Tile.HIGHLIGHT.LOSE;

		tiles[index].background = color;
		tiles[index].setBackground(color);
	}

	//Clean up the board completely making the board ready for a new game
	public void clearBoard()
	{
		for (int i = 0; i < 9; i++) 
		{
			tiles[i].value = "";
			tiles[i].overlay = Tile.HIGHLIGHT.NORMAL;
		}
		reloadColors();
	}
	
	//Reloads the Tiles in the board to use the current Settings variables
	//Useful for when you've changed some of the settings
	public void reloadColors()
	{
		gridPanel.setBackground(settings.BACKGROUND_COLOR);
		for (int i = 0; i < 9; i++) 
		{
			tiles[i].background = settings.TILE_COLOR;
			tiles[i].setBackground(settings.TILE_COLOR);
			tiles[i].repaint();
		}
	}
}
