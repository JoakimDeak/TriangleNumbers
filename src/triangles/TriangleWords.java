package triangles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TriangleWords {

	public static void main(String[] args) {

		// 29 triangle numbers to account for words consisting of 16 Zs
		int[] numbers = createArray(29);

		File file = new File("words.txt");

		Scanner sc = null; // declared null outside of the scope of the try catch blocks to make it visible to both

		try {
			
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			
			System.out.println("could not find the file");
		}

		if (sc != null) { // if scanner was created properly

			try {
				
				System.out.println(countTriangleWords(numbers, sc));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} finally { // makes sure the scanner is closed even if an exception is thrown
				
				sc.close(); 
			}
		}

	}

	public static int[] createArray(int size) {

		int[] triangleNumbers = new int[size];

		for (int i = 1; i <= size; i++) {

			triangleNumbers[i - 1] = (int) ((1.0 / 2.0) * i * (i + 1.0)); // formula for calculating triangle numbers
		}

		return triangleNumbers;
	}

	public static int getValueOfChar(char inputChar) {

		char input = inputChar;

		int output = (int) input - 64; // (int) 'A' would return 65 but I want it to be 1

		return output;
	}

	public static int getValueOfWord(String inputWord) {

		String input = inputWord;
		input = input.toUpperCase(); // dont want 'a' and 'A' to have different values
		char[] chars = input.toCharArray();

		int outputValue = 0;

		for (char character : chars) {

			outputValue += getValueOfChar(character);
		}

		return outputValue;
	}

	public static boolean isTriangleWord(String word, int[] numbers) {

		int valueOfWord = getValueOfWord(word);

		int i = 0;
		while (valueOfWord != numbers[i]) { // searches for the value in the array to find a match

			if (i == numbers.length - 1) {

				return false; // reached end of array without a match
			} else {

				i++;
			}
		}

		return true;
	}

	public static int countTriangleWords(int[] numbers, Scanner sc) throws FileNotFoundException {

		int numOfTriWords = 0;

		while (sc.hasNextLine()) {

			String thisLine = sc.nextLine();
			String currentWord = "";

			for (int i = 0; i < thisLine.length(); i++) {

				char currentChar = thisLine.charAt(i);

				if (currentChar != ('"') && currentChar != ',') { // ignores seperator characters

					currentWord += currentChar;

				} else if (currentChar == ',') { // ',' means end of word

					if (isTriangleWord(currentWord, numbers)) {
						
						numOfTriWords++;
					}

					currentWord = ""; // clears string for next word
				}
			}

		}

		return numOfTriWords;
	}
}
