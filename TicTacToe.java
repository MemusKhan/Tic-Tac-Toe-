import java.util.Scanner;

public class TicTacToe {
	
	private enum cellState {
		X,
		O,
		EMPTY
	}
	private enum gameState {
		WIN,
		DRAW,
		CONTINUE //meaning CONTINUEinue
	}
	Scanner input = new Scanner(System.in);
	//board is an even 3x3 grid so only this one variable is needed
	private int boardSize = 3; 
	private cellState[][] gameBoard = new cellState[boardSize][boardSize];
	private int turnCount = 0;
	private int[][] winConditions = {
			//Horizontal Wins
			{0,0},{0,1},{0,2},
			{1,0},{1,1},{1,2},
			{2,0},{2,1},{2,2},
			//Vertical Wins
			{0,0},{1,0},{2,0},
			{0,1},{1,1},{2,1},
			{0,2},{1,2},{2,2},
			//Diagonal Wins
			{0,0},{1,1},{2,2},
			{0,2},{1,1},{2,0}};
	
	public TicTacToe() {	
		//Fills board with empty cells
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				gameBoard[i][j] = cellState.EMPTY;
			}
		}
		play();
		//Test Code
		/*gameBoard[1][2] = cellState.O;
		gameBoard[2][0] = cellState.X;
		gameBoard[1][1] = cellState.X;
		gameBoard[0][2] = cellState.X;
		printBoard();*/
		
	}
	
	private void play() {
		//Initiating info for game start
		gameState endOfGame = gameState.CONTINUE;
		boolean proceedMove = false;
		System.out.printf("Tic Tac Toe %nSelect rows and columns with numbers 1-3 %n");
		printBoard();
		//Main gameplay loop
		while (endOfGame == gameState.CONTINUE) {
			turnCount++;
			System.out.printf("Player %s's turn%n", whosTurn());
			
			while (proceedMove == false) {
				System.out.println("Choose cell row");
				int choiceX = input.nextInt() - 1; //selection is 1-3 so take off 1 since computers use 0-2
				System.out.println("Choose cell column");
				int choiceY = input.nextInt() - 1;
				proceedMove = cellChoice(choiceX, choiceY);
			}
			
			proceedMove = false;
			printBoard();
			endOfGame = gameStatus();
		}
	}
	
	public void printBoard() {
		//header for board
		System.out.printf(" _________________%n");
		for (int i = 0; i < boardSize; i++) {	
			System.out.printf("|     |     |     |%n");
			//gets the cells for each row and prints them
			System.out.printf("|  %s  |  %s  |  %s  |%n", 
	                         getCell(i,0), getCell(i,1), getCell(i,2));
			System.out.printf("|_____|_____|_____|%n");
		
		}
	}
	
	private String getCell(int row, int column) {
		String cell = gameBoard[row][column].toString();
		//Empty so it should return a blank space
		if (cell.equals("EMPTY"))
			cell = " ";
		return cell;
	}
	
	private boolean validMove(int row, int column) {
		String cellData = getCell(row, column);
		if (cellData == "X" || cellData == "O") {
			System.out.println("Invalid Move");
			return false;
		}
		return true;
	}
	
	private boolean cellChoice(int row, int column) {
		//input validation
		if (row <= 2 && row >= 0 && column <= 2 && column >= 0) {
			if (validMove(row, column) == true) { 
				gameBoard[row][column] = whosTurn();
				return true;
			}
			return false;
		}
		System.out.println("Selection out of bounds");
		return false;
	}
	
	private cellState whosTurn() {
		//Alternates between 0 and 1 indicating who's turn it is
		if (turnCount % 2 == 1)
			return cellState.X;
		else
			return cellState.O;
	}
	
	private gameState gameStatus() {
		cellState whosTurn = null;
		whosTurn = whosTurn();
		
		int threeInARow = 0;
		int checkedThree = 0;
		//scanning through board for a win for the current player
		for(int[] a: winConditions) {
			//whenever it has checked a row of three, reset both counts
			if (checkedThree == 3) {
				checkedThree = 0;
				threeInARow = 0;
			}
			
			if (getCell(a[0],a[1]).equals(whosTurn.toString())) {
				threeInARow++;
			}
			
			checkedThree++;
			
			if (threeInARow == 3) {
				//printBoard();
				System.out.printf("Player %s won", whosTurn);
				return gameState.WIN;
			}
		}
		//Board gets filled up after all nine spaces are filled constituting a draw
		if (turnCount != 9)
			return gameState.CONTINUE;
		else {
			//printBoard();
			System.out.println("It's a tie");
			return gameState.DRAW;
		}
	}

}