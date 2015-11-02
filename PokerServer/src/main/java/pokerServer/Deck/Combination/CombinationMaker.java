package pokerServer.Deck.Combination;

import java.util.List;

import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Hand;
public interface CombinationMaker {
	

List<Hand> generateCombinations(Card[] hand, Card[] table);

Hand getBestHand(Card[] hand, Card[] table);	
	
}
