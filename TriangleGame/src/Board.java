import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JRadioButton;

public class Board {
	private int numOfPins;
	private ArrayList<Move> bestMoves;
	private char[][] firstEntered;
	private Character[][] internal;
	private ArrayList<Character[][]> diffSteps;
	private HashMap<String, ArrayList<Move>> possibleMoves;
	
	// testing edit
	// edit #2
	// Constructor
	// Put the pins in  that the user enters
	public Board(JRadioButton[][] havePin) {
		diffSteps = new ArrayList<>();
		internal = new Character[5][5];
		firstEntered = new char[5][5];
		bestMoves = new ArrayList<>();
		possibleMoves = new HashMap<String, ArrayList<Move>>();
		// convert it into an array of character
		// can mess with this without changing the 
		// graphics
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < row + 1; col++) {
				if(havePin[row][col].isSelected()) {
					setPin(row, col);
					firstEntered[row][col] = '?';
				}
				else {
					internal[row][col] = '.';
					firstEntered[row][col] = '?';
				}
			}
		}
	}
	
	// remove the pin at a specific place
	public void removePin(int row, int col) {
		internal[row][col] = '.';
		numOfPins--;
	}
	
	// add the pin to a specific place
	public void setPin(int row, int col) {
		internal[row][col] = '?';
		numOfPins++;
	}
	
	// find all possible moves with the current state
	private ArrayList<Move> findAllPossibleMoves(ArrayList<Move> tempAllMoves) {
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < row + 1; col++) {
				if(internal[row][col] == '?') {
					movesHelper(tempAllMoves, row, col);
				}
			}
		}
		return tempAllMoves;
	}
	
	// Use to help look at the six different directions that can happen
	private void movesHelper(ArrayList<Move> tempAllMoves, int row, int col) {
		int totalMoves = 6;
		// six different direction
		int[] dirRow = {2, 0, -2, 0, 2, -2};
		int[] dirCol = {0, 2, 0, -2, 2, -2};
		for(int i = 0; i < totalMoves; i++) {
			// make sure move is legit
			int newRow = row + dirRow[i];
			int newCol = col + dirCol[i];
			int removeRow = row + dirRow[i] / 2;
			int removeCol = col + dirCol[i] / 2;
			// make sure movie is legit
			if(newRow >= 0 && newRow < 5 && newCol >=0 && newCol < 5 ) {
				// Yuck, gacky code
				if(internal[newRow][newCol] == null || internal[removeRow][removeCol] == null) {
					// do nothing
				}
				else if(internal[newRow][newCol] == '.' && internal[removeRow][removeCol] == '?') {
					// Since new move is possible, we add it to the array list
					Move newMove = new Move(row, col, row + dirRow[i], col + dirCol[i]);
					newMove.setRemoved(row + dirRow[i] / 2, col + dirCol[i] / 2);
					tempAllMoves.add(newMove);
				}
				else {
					// do nothing
				}
			}
		}
	}
	
	// Find the best solution to the current state
	public int findSolution() {
		int bestCase = findSolutionHelper(1);
		if(bestCase == 1) {
			return 1;
		}
		bestCase = findSolutionHelper(bestCase);
		System.out.println(bestCase);
		for(Move temp : bestMoves) {
			System.out.println(temp.getString());
		}
		return bestCase;
	}
	
	// recursion
	public int findSolutionHelper(int bestCase) {
		ArrayList<Move> allMoves = new ArrayList<>();
		// find all the moves for each new state
		allMoves = findAllPossibleMoves(allMoves);
		int count = numOfPins;
		// base case, out of moves
		if(allMoves.isEmpty()) {
			return count;
		}
		for(Move move : allMoves) {
			removePin(move.getCurrentRow(), move.getCurrentCol());
			removePin(move.getRemoveRow(), move.getRemoveCol());
			setPin(move.getNewRow(), move.getNewCol());
			bestMoves.add(move);
			int temp = findSolutionHelper(bestCase);
			// best case scenario
			if(temp == bestCase) {
				return bestCase;
			}
			// found better solution, not sure if it is the best yet
			if(temp < count) {
				count = temp;
			}
			// bummer, solution were not better
			// backtrack
			bestMoves.remove(move);
			setPin(move.getCurrentRow(), move.getCurrentCol());
			setPin(move.getRemoveRow(), move.getRemoveCol());
			removePin(move.getNewRow(), move.getNewCol());

			
		}
		// return if cannot find
		return count;
	}
	
	// used for debugging purposes
	public ArrayList<String> translateMovesToStrings(){
		ArrayList<String> result = new ArrayList<>();
		for(Move m : bestMoves) {
			result.add(m.getString());
		}
		return result;
	}
	
	public ArrayList<Move> getDiffSteps(){
		return bestMoves;
	}
	
	// override toString method
	// used for debugging purposes
	public String toString() {
		String result = "";
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < i + 1; j++) {
				result += internal[i][j];
			}
			result += "\n";
		}
		return result;
		
	}
}
