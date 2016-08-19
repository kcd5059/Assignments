package playingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import playingcards.Card.Face;
import playingcards.Card.Suit;

public class BlackJack {

	static List<Card> deck = new ArrayList<>();
	static List<Card> dealersHand = new ArrayList<>();
	static List<Card> playersHand = new ArrayList<>();
	static int playerScore = 0;
	static int dealerScore = 0;

	public static void main(String[] args) {

		// Build the deck and shuffle
		buildDeck(1);
		shuffleDeck();

		// Deal the cards and display both hands
		initialDeal();
		showHands();

		// Check if either player already has Black Jack
		if (isAutoWin(dealersHand)) {
			showHands();
			System.out.println("Dealer has Black Jack! You lose.");
			System.exit(0);
		} else if (isAutoWin(playersHand)) {
			showHands();
			System.out.println("You got Black Jack! You Win!");
			System.exit(0);
		}

		// Enter the player phase
		playerPhase();
		// Enter the dealer phase
		dealerPhase();
		// Enter the final phase and display the result of the deal
		finalPhase();

	}

	public static void buildDeck(int deckCount) {

		// Build list of suits to loop through
		List<Suit> suits = new ArrayList<>();
		suits.add(Suit.HEARTS);
		suits.add(Suit.DIAMONDS);
		suits.add(Suit.CLUBS);
		suits.add(Suit.SPADES);

		// For each suit, add these cards to deck for desired number of decks
		// (deckCount)
		for (int i = 0; i < deckCount; i++) {
			for (Suit suit : suits) {
				deck.add(new Card(suit, Face.ACE, 11, true, 1, false));
				deck.add(new Card(suit, Face.KING, 10, true, false));
				deck.add(new Card(suit, Face.QUEEN, 10, true, false));
				deck.add(new Card(suit, Face.JACK, 10, true, false));
				deck.add(new Card(suit, Face.TEN, 10, true, false));
				deck.add(new Card(suit, Face.NINE, 9, true, false));
				deck.add(new Card(suit, Face.EIGHT, 8, true, false));
				deck.add(new Card(suit, Face.SEVEN, 7, true, false));
				deck.add(new Card(suit, Face.SIX, 6, true, false));
				deck.add(new Card(suit, Face.FIVE, 5, true, false));
				deck.add(new Card(suit, Face.FOUR, 4, true, false));
				deck.add(new Card(suit, Face.THREE, 3, true, false));
				deck.add(new Card(suit, Face.TWO, 2, true, false));
			}
		}

	}

	public static void shuffleDeck() {
		Collections.shuffle(deck, new Random());
		Collections.shuffle(deck, new Random());
	}

	public static void initialDeal() {
		// Draw two cards for player
		drawPlayer();
		drawPlayer();
		// Draw two cards for dealer, set one face down
		drawDealer();
		drawDealer();
		dealersHand.get(0).setIsVisible(false);
	}

	public static boolean drawPlayer() {

		// Find cards not yet drawn and add them to player's hand, then set it
		// to drawn
		for (Card card : deck) {
			if (!card.getIsDrawn()) {
				playersHand.add(card);
				card.setIsDrawn(true);
				return true;
			}
		}
		return false;
	}

	public static boolean drawDealer() {

		// Find cards not yet drawn and add them to dealers's hand, then set it
		// to drawn
		for (Card card : deck) {
			if (!card.getIsDrawn()) {
				dealersHand.add(card);
				card.setIsDrawn(true);
				return true;
			}
		}
		return false;
	}

	public static void showHands() {

		// Print out both dealer and player's hands
		System.out.println("--Dealer's Hand---");
		for (Card card : dealersHand) {
			if (card.getIsVisible()) {
				System.out.println(card.getFace() + " of " + card.getSuit());
			} else {
				System.out.println("Face-Down Card");
			}
		}
		System.out.println("");
		System.out.println("---Player's Hand---");
		for (Card card : playersHand) {
			System.out.println(card.getFace() + " of " + card.getSuit());
		}
		System.out.println("---------------------------");
	}

	public static boolean isAutoWin(List<Card> hand) {
		boolean aceFlag = false;
		boolean tenValueFlag = false;

		// See if there is an ace and a card with a value of 10
		for (Card card : hand) {
			if (card.getPrimaryValue() == 11) {
				aceFlag = true;
			} else if (card.getPrimaryValue() == 10) {
				tenValueFlag = true;
			}
		}

		// If ace and ten-value card present, reveal dealer's hand and return
		// true
		if (aceFlag && tenValueFlag) {
			for (Card card : dealersHand) {
				if (!card.getIsVisible()) {
					card.setIsVisible(true);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static void playerPhase() {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		boolean keepGoing = true;

		// Ask player if they want to Hit or settle with their hand, continue
		// until non-"Y" value received
		do {
			System.out.printf("Your hand's current score is %d. Hit? Y/N\n", getHandValue(playersHand));
			String input = scanner.nextLine().trim().substring(0, 1).toUpperCase();

			if (input.equals("Y")) {
				drawPlayer();
				showHands();
			} else {
				keepGoing = false;
			}

			// Determine is player is bust and end game if so
			if (getHandValue(playersHand) > 21) {
				revealDealersHand();
				showHands();
				System.out.println("\nBust! You lose!");
				System.exit(0);
			}
		} while (keepGoing);
	}

	public static void dealerPhase() {
		System.out.println("Entering dealer phase...");

		revealDealersHand();
		showHands();

		// Dealer auto-hits if current value is less than 16
		while (getHandValue(dealersHand) < 16) {
			System.out.println("Dealer: Hit me! \n");
			drawDealer();
			showHands();
			// Check if dealer is bust and end game if so
			if (getHandValue(dealersHand) > 21) {
				System.out.println("Dealer's bust! You win!");
				System.exit(0);
			}
		}
	}

	public static int getHandValue(List<Card> hand) {

		int handValue = 0; // Tracks current value of hand
		int aceCounter = 0; // Counter for number of aces in hand

		// If the hand contains an Ace
		if (hasAce(hand)) {
			// Find the ace and add its primaryValue to handValue first
			for (Card card : hand) {
				if (card.getPrimaryValue() == 11) {
					handValue += 11;
					aceCounter++;
				}
			}
			// Find all non-ace cards and add their value to the handValue
			for (Card card : hand) {
				if (card.getPrimaryValue() != 11) {
					/*
					 * Check if the card value will cause a bust, and remove 10
					 * if an ace's secondary value can still be used
					 */
					if (((card.getPrimaryValue() + handValue) > 21) && (aceCounter > 0)) {
						handValue += card.getPrimaryValue() - 10;
						aceCounter--;
					} else { // Otherwise add the value of the card to the
								// handValue
						handValue += card.getPrimaryValue();
					}
				}
			}
		} else { // If there are no aces, simply add the value of each card to
					// the handValue
			for (Card card : hand) {
				handValue += card.getPrimaryValue();
			}
		}
		return handValue;
	}

	public static void finalPhase() {
		// Determine the outcome of the game and report the result
		if (getHandValue(playersHand) > getHandValue(dealersHand)) {
			System.out.println(getHandValue(dealersHand) + " vs " + getHandValue(playersHand) + " You win!");
		} else if (getHandValue(playersHand) == getHandValue(dealersHand)) {
			System.out.println("Tie game!");
		} else {
			System.out.println(getHandValue(dealersHand) + " vs " + getHandValue(playersHand) + " Dealer wins!");
		}

	}

	public static boolean hasAce(List<Card> hand) {
		boolean hasAce = false;

		// Check if the given hand has an ace and return true if so
		for (Card card : hand) {
			if (card.getPrimaryValue() == 11) {
				hasAce = true;
			}
		}
		return hasAce;
	}
	
	public static void revealDealersHand() {
		
		//Set all face-down cards in dealer's hand to face-up
		for (Card card : dealersHand) {
			if (!card.getIsVisible()) {
				card.setIsVisible(true);
			}
		}
	}
}
