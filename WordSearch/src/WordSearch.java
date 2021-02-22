import java.util.Arrays;
import java.util.LinkedList;
/**
 *  
 * @author Stephanie Ball
 *
 */
public class WordSearch {

	public static void main(String args[]) {

		// This input grid is used to test. 
		// Search can only move to the right or below. 
		String[][] grid = { { "s", "a", "s", "x", "i" },
							{ "x", "c", "z", "b", "a" }, 
							{ "m", "a", "i", "v", "z" },
							{ "n", "t", "n", "i", "w" }, 
							{ "n", "p", "s", "p", "p" }, 
							{ "r", "o", "s", "k", "P" } };
		
		// wordSearch() is a method that scans a given grid for a given word.
		// This is a method that I was asked to implement for my coding challenge.
		// wordSearch() returns a 2d int array that contains the coordinates for each
		// letter of the word found. 
		int[][] wordGrid = wordSearch(grid, "catnip");
		
		// printPoints() is a method that takes in a 2d int array. It scans the contents
		// of the array to see if the word was found. If not, it prints out a statement "word not found.".
		// If the word was found, it returns a printed list of the coordinates for each letter
		// of the word. 
		printPoints(wordGrid);

	}

	/**
	 *  This method uses two stacks, one to keep track of of the possible paths and the other to 
	 *  keep track of the target letter. If the one path comes to a dead end and it needs to backtrack,
	 *  the target letter will backtrack accordingly.
	 * @param grid
	 * @param word
	 * @return int[][] pointResult
	 */
	public static int[][] wordSearch(String[][] grid, String word) {
		
		int wordLength = word.length();
		int[][] pointResult = new int[wordLength][2];
		
		// These are the two stacks that will be used. uncheckedPnts will keep track of possible paths
		// and targetIndex will keep track of the target letter. 
		LinkedList<int[]> uncheckedPnts = new LinkedList<int[]>();
		LinkedList<Integer> targetIndex = new LinkedList<Integer>();


		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				
				// Sets target to 0 because I only want to search for the 
				// first letter in the word at this stage. 
				int target = 0;
				String compareChar = grid[i][j];  // Store the current letter into compareChar so we can compare it to the current target. 
				String targetChar = Character.toString(word.charAt(target));  // Stores the letter we are currently looking for into targetChar.  

				// Compares current letter with the target letter to see if it's a match. 
				// If it is a match, the coordinates of the letter will be pushed into a stack (uncheckedPnts).
				// The desired target will be pushed into targetIndex list at the same index as the letter in uncheckedPnts.
				// This is done so we know which target letter we were looking for when the letter to compare was stored. 
				if (targetChar.equals(compareChar)) {
					int[] startPnt = { i, j };
					uncheckedPnts.push(startPnt);
					targetIndex.push(target);
				}

				// Now that the first letter is found, this while loop will continue looking for the 
				// the rest of the letters in the word. 
				while (uncheckedPnts.size() != 0) {
					int[] comparePnt = uncheckedPnts.pop();
					int rowIndex = comparePnt[0];
					int colIndex = comparePnt[1];
					compareChar = grid[rowIndex][colIndex]; // Retrieving letter at coordinates from list.
					target = targetIndex.pop();
					targetChar = Character.toString(word.charAt(target)); // Retrieving letter at target index.

					// Comparing the current letter with target letter.
					if (targetChar.equals(compareChar)) {
						// Store coordinates of matching letter.
						pointResult[target][0] = rowIndex;
						pointResult[target][1] = colIndex;
						target++; // Increases target to start search for next letter in the word. 

						// Checks if there's a letter to the right and stores in uncheckedPnts if true.
						if (colIndex < grid[i].length) {
							int[] rightPnt = { rowIndex, colIndex + 1 };
							uncheckedPnts.push(rightPnt);
							targetIndex.push(target);
						}

						// Checks if there's a letter below and stores in uncheckedPnts if true.
						if (rowIndex < grid.length) {
							int[] belowPnt = { rowIndex + 1, colIndex };
							uncheckedPnts.push(belowPnt);
							targetIndex.push(target);
						}
					}
					// This will exit the while loop and return 2d int array (pointResult)
					// when all letters in the word have been found. 
					if (target >= wordLength) {
						return pointResult;
					}
				}
			}
		}

		return pointResult;
	}

	/**
	 * This method scans the given 2d int array to see if the word was found
	 * and prints out the coordinates for each letter of the word if true.
	 * If the word was not found, "Word was not found." will be printed. 
	 * @param points
	 */
	public static void printPoints(int[][] points) {
		boolean matchFound = true;

		// If coordinates are found set to index[0,0] that is not the
		// first letter coordinates, the word has not been found.
		// This is because the search can only move to the right or below
		// the most recently found letter. 
		for (int i = 1; i < points.length; i++) {
			if (points[i][0] == 0 && points[i][1] == 0) {
				matchFound = false;
			}
		}
		
		if (!matchFound) {
			System.out.println("Word was not found.");
		} else {
			for (int[] row : points) {
				System.out.println(Arrays.toString(row));
			}
		}

	}

}
