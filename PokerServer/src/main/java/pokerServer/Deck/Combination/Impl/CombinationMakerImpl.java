package pokerServer.Deck.Combination.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pokerServer.Deck.Hand;
import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Combination.CardCombinantion;
import pokerServer.Deck.Combination.CombinationMaker;

@Service
public class CombinationMakerImpl implements CombinationMaker {

	@Override
	public List<Hand> generateCombinations(Card[] hand, Card[] table) {

		List<Card> allCards = new ArrayList<Card>();
		Set<Card[]> combinations = new HashSet<Card[]>();
		initListAllCard(hand, table, allCards);
		int card = 0;

		generateCombinations(allCards, combinations);

		return combinations.stream().map(p -> {
			return new Hand(p);
		}).collect(Collectors.toList());
	}

	private void generateCombinations(List<Card> allCards, Set<Card[]> combinations) {
		Card[] combination;
		for (int i = 0; i < allCards.size(); i++) {

			for (int j = i + 1; j < i + 4; j++) {
				combination = new Card[5];
				combination[0] = allCards.get(i);
				for (int k = 1; k < (combination.length); k++) {
					combination[(k) % 5] = allCards.get((j + k - 1) % allCards.size());

				}
				combinations.add(combination);
			}
		}
	}

	@Override
	public Hand getBestHand(Card[] hand, Card[] table) {

		List<Hand> possibleCombinations = generateCombinations(hand, table);

		Collections.sort(possibleCombinations);
		Collections.reverse(possibleCombinations);
		
	
		return possibleCombinations.get(0);

	}

	private void initListAllCard(Card[] hand, Card[] table, List<Card> allCards) {
		allCards.add(hand[0]);
		allCards.add(hand[1]);

		allCards.add(table[0]);
		allCards.add(table[1]);
		allCards.add(table[2]);
		allCards.add(table[3]);
		allCards.add(table[4]);
	}

}
