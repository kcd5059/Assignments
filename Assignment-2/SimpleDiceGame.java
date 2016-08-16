package simpledicegame;

import java.util.Date;
import java.util.Random;

public class SimpleDiceGame {

	public static void main(String[] args) {
		//Start the game
		play();
	}
	
	public static void play() {
		int score = 0;
		int rollDie1 = 0;
		int rollDie2 = 0;
		int rolls = 0;
		long seed = new Date().getTime();
		Random rnd = new Random(seed);
		
		do {
			//Increment roll count on each loop
			rolls++;
			//Calculate the score of previous roll
			score += (rollDie1 + rollDie2);
			
			//Roll both dice individually
			rollDie1 = rnd.nextInt(6) + 1;
			rollDie2 = rnd.nextInt(6) + 1;
			
		//Roll again if summation of dice does not equal 7
		} while ( (rollDie1 + rollDie2) != 7 );
		

		//Print result when the game ends
		System.out.println("After " + rolls + " rolls, you scored " + score + " before rolling a 7.");
	}

}
