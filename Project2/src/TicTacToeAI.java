import java.util.Random;

//TicTacToeAI controls the computer's decision making
class TicTacToeAI 
{
	static char[] chars = {'X', 'O'};
	static Random rand = new Random(System.currentTimeMillis());

	//Evaluate the board and choose the best move based on the move evaluations
	public static int chooseMove(char[] board, int depth, int playerIndex)
	{
		int[] moves = GameLogic.getAvailableMoves(board);
		int[] values = evaluateBoard(board, moves, depth, playerIndex);

		//Make sure there was actually a valid move
		if(values == null)
			return -1;

		return moves[findMax(values)];
	}

	//Check if move is a winning move and if it's not then evaluate every possible response/counter-response 
	//until depth has been reached or every possibility has been evaluated
	public static int evaluateMove(char[] board, int moveIndex, int depth, int playerIndex)
	{
		char playerChar = chars[playerIndex];
		//Check to See if it's a winning move or we've gone too deep
		if(depth <= 0)
			return 0;
		if(GameLogic.checkForWin(board, moveIndex, playerChar))
		{
			return 1;
		}

		//Create a temporary board including this move and pass that on to the next Evaluation call
		char[] tempBoard = board.clone();
		tempBoard[moveIndex] = playerChar;

		//Make sure there are actually some moves left
		int[] moves = GameLogic.getAvailableMoves(tempBoard);
		int[] values = evaluateBoard(tempBoard, moves, depth - 1, (playerIndex + 1) % 2);

		if(values == null)
			return 0;

		return -1 * values[findMax(values)];
	}

	//Evaluate every move and put its value into an int array
	public static int[] evaluateBoard(char[] board, int[] moves, int depth, int playerIndex)
	{
		if(moves.length == 0)
			return null;

		int[] values = new int[moves.length];

		for(int i = 0; i < moves.length; i++)
		{
			values[i] = evaluateMove(board, moves[i], depth, playerIndex);
		}
		return values;
	}

	//Finds index of max value in array.
	//If multiple max values exist it will randomly decide which one to go with
	//Helps add some unpredictability to the Computer's moves while not compromising its effectiveness
	public static int findMax(int[] array)
	{
		int maxIndex = 0;

		for(int i = 0; i < array.length; i++)
		{

			if(array[i] > array[maxIndex])
			{
				maxIndex = i;
			}
			//33% chance to swap if it's exactly equal to the max
			if(array[i] == array[maxIndex])
			{
				maxIndex = (rand.nextInt() % 3 == 0 ? i : maxIndex); 
			}
		}

		return maxIndex;
	}

	//Same as findMax function except it finds the minimum instead
	public static int findMin(int[] array)
	{
		int minIndex = 0;

		for(int i = 0; i < array.length; i++)
		{
			if(array[i] < array[minIndex])
			{
				minIndex = i;
			}
			if(array[i] == array[minIndex])
			{
				minIndex = (rand.nextInt() % 3 == 0 ? i : minIndex); 
			}
		}

		return minIndex;
	}
}
