package pokerServer.Deck.Card;


public class Card {

    final Rank rank;
    final Suit suit;

    public Card (final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		return rank + ":" + suit;
	}
    
	
}
