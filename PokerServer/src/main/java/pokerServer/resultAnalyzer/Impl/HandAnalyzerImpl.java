package pokerServer.resultAnalyzer.Impl;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pokerServer.Client.Client;
import pokerServer.Deck.Hand;
import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Combination.CombinationMaker;
import pokerServer.broadcaster.Broadcast;
import pokerServer.resultAnalyzer.HandAnalyzer;

@Service
public class HandAnalyzerImpl implements HandAnalyzer {

	@Autowired
	CombinationMaker combinationMaker;
	
	@Autowired
	Broadcast broadcaster;

	@Override
	public Client whichClientWin(List<Client> player, Card[] table) throws Exception {

		for (Client c : player) {
			Hand best = combinationMaker.getBestHand(c.getHand(), table);

			c.setBestCombinationCard(best);
			
			broadcaster.broadcast("Masz w reku "+c.getBestCombinationCard().getFigure(), c);

		}
		Collections.sort(player);
		Collections.reverse(player);
		return player.get(0);
	}

}
