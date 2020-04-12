package csc312;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project1 {

	/**
	 * Perform an HTTP request to a website and return the result. If within 5 tries
	 * and the server is not responding then we crash the program.
	 * 
	 * @param query Query to access
	 * @return Response
	 */
	private static String getData(String query) {
		for (int retries = 0; retries < 5; retries++) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(query).openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", "Mozilla/5.0");

				if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
					throw new Exception();

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null)
					response.append(inputLine + "\n");

				in.close();

				return response.toString().trim();
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		System.exit(0);
		return null;
	}

	/**
	 * Return a grid of letters.
	 * 
	 * @param gameNumber The puzzle number
	 * @param size       The size of the grid
	 * @return Grid of letters
	 */
	public static char[][] loadPuzzle(int gameNumber, int size) {
		char[][] puzzle = new char[size][size];

		// Contact the website to get the letters for each position in the grid
		for (int row = 0; row < size; row++) {
			char colName = 'a';

			for (int col = 0; col < size; col++) {
				String response = getData("https://wordfinder-001.appspot.com/wordfinder?game=" + gameNumber + "&pos="
						+ colName + (row + 1));

				puzzle[row][col] = response.charAt(0);
				colName++;
			}
		}

		return puzzle;
	}

	/**
	 * Load the words that is going to be tried in the puzzle.
	 * 
	 * @return List of words
	 */
	public static List<String> loadWords() {
		List<String> words = new ArrayList<>();
		Scanner scanner = new Scanner(getData("https://wordfinder-001.appspot.com/word.txt"));

		while (scanner.hasNextLine())
			words.add(scanner.nextLine());

		return words;
	}

	/**
	 * Convert a given coordinate to a game readable coordinate.
	 * 
	 * @param row Row coordinate in the puzzle
	 * @param col Column coordinat in the puzzle
	 * @return Game coordinate
	 */
	public static String convertCoordinateToString(int row, int col) {
		return "" + (char) (col + 'A') + (row + 1);
	}

	/**
	 * Attempt to find if the word is in the puzzle
	 * 
	 * @param puzzle Puzzle where to find word
	 * @param word   Word to find
	 * @return Location of the word in the puzzle
	 */
	public static String findWordInPuzzle(char[][] puzzle, String word) {
		/*
		 * PSEUDO-CODE:
		 * Let directions = east, south-east, south, south-west, west, north-west, and north
		 * 
		 * For each row in the puzzle:
		 *   For each column in the row:
		 *     For each direction:
		 *       If word matches at the given direction:
		 *         Return the start and end coordinates as answer
		 *       End If
		 *     End For
		 *   End For
		 * End For
		 * 
		 * If all slots in the puzzle has been scanned and nothing returned then word is not in the puzzle
		 */
		final int[] rowIncrement = { +0, +1, +1, +1, +0, -1, -1, -1 };
		final int[] colIncrement = { +1, +1, +0, -1, -1, -1, +0, +1 };

		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[row].length; col++) {
				// Search all directions east, south-east, south, south-west, west, north-west,
				// north, north-east
				for (int i = 0; i < 8; i++) {
					int currentRow = row;
					int currentCol = col;
					int nextLetterIndex = 0;

					while (nextLetterIndex < word.length()) {
						if (puzzle[currentRow][currentCol] != word.charAt(nextLetterIndex))
							break;

						// Move to the next letter
						nextLetterIndex++;
						currentRow += rowIncrement[i];
						currentCol += colIncrement[i];

						if (currentRow < 0 || currentRow >= puzzle.length || currentCol < 0
								|| currentCol >= puzzle[row].length)
							break;
					}

					if (nextLetterIndex >= word.length())
						// If all letters matched, then we found the word
						return convertCoordinateToString(row, col) + ":"
								+ convertCoordinateToString(currentRow, currentCol);
				}

			}
		}

		return null;
	}

	/**
	 * Print the puzzle
	 * 
	 * @param puzzle Puzzle to print
	 */
	public static void printPuzzle(char[][] puzzle) {
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[row].length; col++)
				System.out.print(puzzle[row][col] + " ");

			System.out.println();
		}
	}

	/**
	 * Entry point of the program to run the word search.
	 * 
	 * @param args Unused arguments
	 */
	public static void main(String[] args) {
		final int gameNumber = 3;
		final int size = 5;

		// Load the word search
		System.out.println("Loading a word search game... please wait, this will take awhile...");
		char[][] puzzle = loadPuzzle(gameNumber, size);
		printPuzzle(puzzle);

		// Load the words to try to find
		System.out.println();
		System.out.println("Loading the words to be tried... please wait, this will take awhile...");

		// Now try and find each word in the board
		List<String> words = loadWords();

		for (String word : words) {
			String location = findWordInPuzzle(puzzle, word);

			if (location != null)
				System.out.println("game" + gameNumber + " word: " + word + " location: " + location);
			else
				System.out.println("game" + gameNumber + " word: " + word + " location: not found");
		}
	}

}
