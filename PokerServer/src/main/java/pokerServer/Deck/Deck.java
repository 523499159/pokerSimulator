package pokerServer.Deck;

import java.util.List;

import pokerServer.Deck.Card.Card;

public interface Deck {

	Card getRandomCardFromDeck();
	List<Card> getRandomizedCards();
}
