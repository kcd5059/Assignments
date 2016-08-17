package simplecraps;

import java.util.Random;

public class SimpleCraps {

	public static void main(String[] args) {

		//Call method that calls play() 100 times and tracks wins/losses
		playNTimes(2147483647);
	}
	
	public static boolean play() {
		
		boolean win = false;
		int rollValue = 0;
		int point = 0;
		@SuppressWarnings("unused")
		int rolls = 0;
		Random rnd = new Random();
		
		//First roll
		rollValue =  (rnd.nextInt(6) + 1) + (rnd.nextInt(6) + 1);
		
		//Determine result of first roll
		// 7 is a win on first roll
		if ( rollValue == 7 ) {
			//System.out.println("You rolled a 7 and won on the first roll!");
			win = true;
			return win;
		  // 2, 3, or 12 on first roll is a lose	
		} else if ( (rollValue == 2) || (rollValue == 3) || (rollValue == 12) ) {
			//System.out.println("You rolled a " + rollValue + " and lost on the first roll!");
			return win;
			
	      // Set the point value since roll was not a winner or a loser and increment roll counter
		} else {
			point = rollValue;
			rolls++;
			//System.out.printf("First roll: Point is set to %d.\n", point);
		}
		
		//Continue rolling if second roll is indecisive
		do {
			//Increment the rolls counter
			rolls++;
			
			//Roll the dice
			rollValue =  (rnd.nextInt(6) + 1) + (rnd.nextInt(6) + 1);
			
			if ( rollValue == point ) {
				//System.out.println("You won after " + rolls + " rolls by rolling " + point + ".");
				win = true;
				return win;
			} else if ( rollValue == 7 ) {
				//System.out.println("You rolled a 7 and lost on roll " + rolls + "!");
				return win;
			}
		} while (win == false);
		
		// Ensure a return value (program should never reach this point)
		return true;
	}
	
	public static void playNTimes(int desiredPlays) {
		
		boolean result = false;
		int wins = 0;
		int losses = 0;

		//Loop and run play() the desired number of times
		for (int i = 0; i < desiredPlays; i++) {
			//Get the result of a single play
			result = play();
			//Increment wins if it's a win
			if (result) {
				wins++;
			//Increment losses if it's a lose
			} else {
				losses++;
			}
			
		}
		//Print result of N rolls
		System.out.println("After " + desiredPlays + " plays, you won " + wins + 
				" times and lost " + losses + " times.");
	}

}
