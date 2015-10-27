package pokerServer.Deck.Impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;
import pokerServer.Deck.Deck;
import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Card.Rank;
import pokerServer.Deck.Card.Suit;

@Component
public class DeckImpl implements Deck {
	
	
private	List<Card> deck;

private DeckImpl() {
deck=new LinkedList<Card>();
		for(Rank r:Rank.values()){
			for(Suit s:Suit.values()){
				deck.add(new Card(r, s));
			}
		}

}

@Override
public Card getRandomCardFromDeck() {
	int idx= ThreadLocalRandom.current().nextInt(0,deck.size() + 1);
return deck.get(idx);
}

@Override
public List<Card> getRandomizedCards() {
	List<Card> copy=new ArrayList<Card>(deck);
	Collections.shuffle(copy);
	return copy;
}
	

}
