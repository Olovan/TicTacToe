import javax.swing.*;

import java.awt.Color;
import java.util.ArrayList;

//Game logic handles the Rules and operation of Tic Tac Toe and keeps track of Game State
public class GameLogic {

	//References to other classes
	private static Board board;
	private static Settings settings;

	public enum GAMESTATE {ACTIVE, AI_WIN, PLAYER_WIN, TIE};
	public enum TURN {PLAYER, COMPUTER}; 

	static public GAMESTATE state = GAMESTATE.ACTIVE;
	static public TURN turn  = TURN.PLAYER;

	//Tracks state of the board with array of chars
	public static char[] textBoard = new char[9];

	//Sets reference to the board so that the game logic can interact with it
	//Also grabs a reference to the current settings from the Board
	public static void setCurrentBoard(Board board)
	{
		GameLogic.board = board;
		settings = board.settings;
	}
	
	//Computer takes a turn and then goes back to waiting for the Player
	public static void aiTurn()
	{
		if(state != GAMESTATE.ACTIVE || turn != TURN.COMPUTER)
		{
			//Don't take a turn if it's not appropriate
			return;
		}
		int aiMove = TicTacToeAI.chooseMove(textBoard, settings.AI_DIFFICULTY, 1);
		if(aiMove >= 0)
		{
			textBoard[aiMove] = 'O';
			board.tiles[aiMove].value = "O";
			board.tiles[aiMove].repaint();
			if(checkForWin(textBoard, aiMove, 'O'))
			{
				highlightWin(aiMove, settings.LOSE_HIGHLIGHT);
				settings.aiScore++;
				gameOver(GAMESTATE.AI_WIN);
				return;
			}
		}
		if(getAvailableMoves(textBoard).length == 0)
		{
			settings.ties++;
			gameOver(GAMESTATE.TIE);
		}
		else
			turn = TURN.PLAYER;
	}

	//Player moves to selected Tile and then tells the computer to take a turn
	public static void playerTurn(Tile tile)
	{
		tile.value = "X";
		textBoard[tile.index] = 'X';
		tile.repaint();
		if(checkForWin(textBoard, tile.index, 'X'))
		{
			highlightWin(tile.index, settings.WIN_HIGHLIGHT);
			settings.playerScore++;
			gameOver(GAMESTATE.PLAYER_WIN);
			return;
		}
		if(getAvailableMoves(textBoard).length == 0)
		{
			settings.ties++;
			gameOver(GAMESTATE.TIE);
		}
		else
		{
			turn = TURN.COMPUTER;
			aiTurn();
		}
	}
	
	//Highlight Winning move
	public static void highlightWin(int moveIndex, Color highlightColor)
	{
		int x = moveIndex % 3;
		int y = moveIndex / 3;
		char ch = textBoard[moveIndex];
		
		if(textBoard[y*3 + ((x + 1) % 3)] == ch && textBoard[y*3 + ((x + 2) % 3)] == ch)
		{
			GameLogic.board.setTileBackground(3*y + 0, highlightColor);
			GameLogic.board.setTileBackground(3*y + 1, highlightColor);
			GameLogic.board.setTileBackground(3*y + 2, highlightColor);
		}
		if(textBoard[((y + 1) % 3) * 3 + x] == ch && textBoard[((y + 2) % 3) * 3 + x] == ch)
		{
			GameLogic.board.setTileBackground(3*0 + x, highlightColor);
			GameLogic.board.setTileBackground(3*1 + x, highlightColor);
			GameLogic.board.setTileBackground(3*2 + x, highlightColor);
		}
		if(textBoard[4] == ch && textBoard[0] == ch && textBoard[8] == ch)
		{
			GameLogic.board.setTileBackground(0, highlightColor);
			GameLogic.board.setTileBackground(4, highlightColor);
			GameLogic.board.setTileBackground(8, highlightColor);
		}
		if(textBoard[2] == ch && textBoard[4] == ch && textBoard[6] == ch)
		{
			GameLogic.board.setTileBackground(2, highlightColor);
			GameLogic.board.setTileBackground(4, highlightColor);
			GameLogic.board.setTileBackground(6, highlightColor);
		}
	}

	//Checks if the input move would win the game
	public static Boolean checkForWin(char[] board, int moveIndex, char playerChar)
	{
		int x = moveIndex % 3;
		int y = moveIndex / 3;
		char[] tempBoard = board.clone();
		tempBoard[moveIndex] = playerChar;

		Color highlightColor = playerChar == 'X' ? settings.WIN_HIGHLIGHT : settings.LOSE_HIGHLIGHT;

		//Check Horizontals
		if(board[y*3 + ((x + 1) % 3)] == playerChar && board[y*3 + ((x + 2) % 3)] == playerChar)
		{
			return true;
		}
		//Check Verticals
		if(board[((y + 1) % 3) * 3 + x] == playerChar && board[((y + 2) % 3) * 3 + x] == playerChar)
		{
			return true;
		}
		//Check Diagonal 1
		if(tempBoard[4] == playerChar && tempBoard[0] == playerChar && tempBoard[8] == playerChar)
		{
			return true;
		}
		//Check Diagonal 2
		if(tempBoard[2] == playerChar && tempBoard[4] == playerChar && tempBoard[6] == playerChar)
		{
			return true;
		}

		return false;
	}

	//Returns an array containing the indices of all available Tiles
	//Array will be empty if there are no available moves (The board is filled up)
	public static int[] getAvailableMoves(char[] board)
	{
		ArrayList<Integer> moves = new ArrayList<Integer>();

		for(int i = 0; i < 9; i++)
		{
			if(board[i] == '\0')
			{
				moves.add(i);
			}

		}

		//Turn the Integer ArrayList into an array of ints
		int[] retVal = new int[moves.size()];
		for(int j = 0; j < retVal.length; j++)
			retVal[j] = moves.get(j);

		return retVal;
	}

	//GAME OVER screen
	public static void gameOver(GAMESTATE state)
	{
		settings.defaultFileExport();
		GameLogic.state = state;
		String title = "";
		String message = 
			"Player: " + settings.playerScore + "\n" +
			"Computer: " + settings.aiScore + "\n"	+
			"Would you like to play again?";
		switch(state)
		{
			case PLAYER_WIN:
				title = "Player Wins!";
				break;
			case AI_WIN:
				title = "Computer Wins!";
				break;
			case TIE:
				title = "Tie!";
				break;
			default:
				title = "ERROR: Unknown State";
				break;
		}
		int again = JOptionPane.showConfirmDialog(GameLogic.board, message, title, JOptionPane.YES_NO_OPTION);
		if(again == JOptionPane.YES_OPTION)
		{
			restartGame();
		}
	}

	public static void restartGame()
	{
		settings.defaultFileExport();
		clearBoard(); //Clear internal state
		board.clearBoard(); //Clear GUI board
		GameLogic.state = GAMESTATE.ACTIVE;
		turn = TURN.PLAYER;
		if(settings.aiMoveFirst)
		{
			turn = TURN.COMPUTER;
			aiTurn();
		}
	}

	//Clears the GameLogic's internal gamestate board
	private static void clearBoard()
	{
		for (int i = 0; i < 9;i++) 
		{
			textBoard[i] = '\0';
		}
	}
}
