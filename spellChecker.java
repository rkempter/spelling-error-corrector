
import java.util.ArrayList;
import java.util.Vector;


public class spellChecker {
	
	private static ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
	private static int cutoff = 2;
	
	
	
	public spellChecker(StringBuffer wordX, String wordY)
	{
		initialization(wordX, wordY);
		
		int editDistance = getEditDistance(wordX, wordY);
		
		// Print matrix for debug
		printMatrix();
		
		System.out.printf("The edit distance is %d", editDistance);
	}
	
	private int getNextState(int state, char edge) {
		return 1;
		//Array<Array<char>> states = new Array<Array<char>>();
	}
	
	private boolean isFinal(int state) {
		if(state == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	private int getEditDistance(StringBuffer wordX, String wordYString) {
		int y = 1;
		StringBuffer wordY = new StringBuffer();
		
		// Cut off distance
		int i = Math.min(wordX.length(), Math.max(1, wordY.length()-cutoff));
		int j = Math.min(wordX.length(), Math.max(1, wordY.length()+cutoff));
		
		for(; y <= wordYString.length(); y++) {
			wordY.append(wordYString.charAt(y-1));
			checkConditions(wordX, wordY);
		}
		
		int x = wordX.length();
		
		return matrix.get(y-1).get(x);
	}
	
	private boolean getCutOffDistance(StringBuffer wordX, StringBuffer wordY) {
		int i = Math.min(wordX.length(), Math.max(1, wordY.length()-cutoff));
		int j = Math.min(wordX.length(), Math.max(1, wordY.length()+cutoff));
		
		int posY = wordY.length();
		int min = matrix.get(posY).get(i);
		for(int k = i; k <= j; k++) {
			if(matrix.get(posY).get(k) < min) {
				min = matrix.get(posY).get(k);
			}
		}
		
		if(min <= cutoff) {
			return true;
		}
		return false;
	}


	private int getMin(int posX, int posY, boolean flag) {
		int value = Math.min(matrix.get(posY-1).get(posX), matrix.get(posY).get(posX-1));
		
		if(flag == true) {
			return 1+Math.min(value, matrix.get(posY-2).get(posX-2));			
		} else {
			return 1+Math.min(value, matrix.get(posY-1).get(posX-1));
		}
	}
	
	private void initialization(StringBuffer wordX, String wordY) {
		for(int y = 0; y <= wordY.length(); y++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			matrix.add(row);
			row.add(y);
			if(y == 0) {
				for(int x = 1; x <= wordX.length(); x++) {
					row.add(x);
				}
			}
		}
	}
	
	private boolean checkConditions(StringBuffer wordX, StringBuffer wordY) {
		if(matrix.size() <= wordY.length()) {
			Vector<Integer> row = new Vector<Integer>();
			row.add(wordY.length()-1);
		}
		
		int y = wordY.length();
		char charY = wordY.charAt(y-1);
		
		for(int x = 1; x <= wordX.length(); x++) {
//			System.out.printf(" %d / %d: y: %c, x: %c \n", y, x, charY, wordX.charAt(x-1));
			if(charY == wordX.charAt(x-1)) {
				//System.out.print("egality");
				matrix.get(y).add(matrix.get(y-1).get(x-1));
			} else if(x >= 2 && y >= 2 && wordX.charAt(x-2) == wordY.charAt(y-1) && wordX.charAt(x-1) == wordY.charAt(y-2)) {
				//System.out.print("transposition");
				matrix.get(y).add(getMin(x, y, true));
			} else {
				//System.out.print("insertion");
				matrix.get(y).add(getMin(x, y, false));
			}
		}
		
		return getCutOffDistance(wordX, wordY);
	}
	
	private void printMatrix() {
		for(int i = 0; i < matrix.size(); i++) {
			System.out.println(matrix.get(i));
		}
	}
	
	public static void main(String [] args) {
		if(args.length >= 2) {
			
			StringBuffer wordX = new StringBuffer(args[0]);
			String wordY = args[1];
			
			spellChecker sp = new spellChecker(wordX, wordY);
		}
	}
}
