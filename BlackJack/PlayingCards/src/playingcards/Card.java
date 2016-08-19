package playingcards;

public class Card {

	private Suit suit;
	private Face face;
	private int primaryValue;
	private boolean isVisible;
	private int secondaryValue;
	private boolean isDrawn;

	
	public Card(Suit suit, Face face, int primaryValue, boolean isVisible, int secondaryValue, boolean isDrawn)
	{
		this.suit = suit;
		this.face = face;
		this.primaryValue = primaryValue;
		this.isVisible = isVisible;
		this.secondaryValue = secondaryValue;
		this.isDrawn = isDrawn;
	}
	
	public Card(Suit suit, Face face, int primaryValue, boolean isVisible, boolean isDrawn)
	{
		this.suit = suit;
		this.face = face;
		this.primaryValue = primaryValue;
		this.isVisible = isVisible;
		this.isDrawn = isDrawn;
	}
	
	public Card(Suit suit, Face face, int primaryValue, boolean isVisible)
	{
		this.suit = suit;
		this.face = face;
		this.primaryValue = primaryValue;
		this.isVisible = isVisible;
	}
	
	public Card(Suit suit, Face face, int primaryValue, int secondaryValue)
	{
		this.suit = suit;
		this.face = face;
		this.primaryValue = primaryValue;
		this.secondaryValue = secondaryValue;
	}
	
	public Card(Suit suit, Face face, int primaryValue)
	{
		this.suit = suit;
		this.face = face;
		this.primaryValue = primaryValue;
	}
	
	public Card(Suit suit, Face face)
	{
		this.suit = suit;
		this.face = face;
	}
	
	public Card(Suit suit) 
	{
		this.suit = suit;
	}
	
	public enum Suit {
		DIAMONDS, HEARTS, SPADES, CLUBS;
	}
	
	public enum Face {
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;
	}
	
	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public int getPrimaryValue() {
		return primaryValue;
	}

	public void setPrimaryValue(int primaryValue) {
		this.primaryValue = primaryValue;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}
	
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getSecondaryValue() {
		return secondaryValue;
	}

	public void setSecondaryValue(int secondaryValue) {
		this.secondaryValue = secondaryValue;
	}
	
	public boolean getIsDrawn() {
		return isDrawn;
	}

	public void setIsDrawn(boolean isDrawn) {
		this.isDrawn = isDrawn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + primaryValue;
		result = prime * result + secondaryValue;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (primaryValue != other.primaryValue)
			return false;
		if (secondaryValue != other.secondaryValue)
			return false;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [suit=" + suit + ", face=" + face + ", primaryValue=" + primaryValue + ", isVisible=" + isVisible
				+ ", secondaryValue=" + secondaryValue + "]";
	}
	
	
	
	
}
