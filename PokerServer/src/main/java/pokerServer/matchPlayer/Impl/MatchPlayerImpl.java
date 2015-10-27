package pokerServer.matchPlayer.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import pokerServer.Client.Client;
import pokerServer.Match.Match;
import pokerServer.broadcaster.Broadcast;
import pokerServer.matchPlayer.MatchPlayer;

@Service
public class MatchPlayerImpl implements MatchPlayer{

	@Autowired
	Broadcast broadcaster;
	
	@Autowired
	Match match;
	
	private int currentDealerIdx;
	private int smallBlindIdx;
	private int bigBlindIdx;
	
	
	@Override
	public void playMatch(List<Client> players) throws Exception {
		System.out.println("Play Match");
		match.initializeNewMatch(players);
		System.out.println("INIT");
		preRound(players);
		System.out.println("AFTER PRE");
		giveTwoCards(players);
		System.out.println("AFTER GIVE TWO");

	
	
	}

	
	
	private void preRound(List<Client> players) throws Exception{
		broadcaster.broadcast("Rozpocznamy mecz", players);	
		broadcaster.broadcast("@SMALLBLIND:"+match.getSmallBlind(), players);		
		broadcaster.broadcast("@BIGBLIND:: "+match.getBigBlind(), players);		
		Client dealer=match.getDealer();		
		setIndexes(players.indexOf(dealer), players);			
	}
	
	private void setIndexes(int dealer, List<Client> players) throws Exception{
		currentDealerIdx=dealer;
		broadcaster.broadcast("Dealerem jest: "+players.get(currentDealerIdx).getName(), players);	
		smallBlindIdx=(currentDealerIdx+1)%players.size();
		bigBlindIdx=(currentDealerIdx+2)%players.size();
		broadcaster.broadcast(players.get(smallBlindIdx).getName()+" placi mala ciemna "+match.getSmallBlind(), players);
		broadcaster.broadcast(players.get(bigBlindIdx).getName()+" placi duza ciemna "+match.getBigBlind(), players);
	}
	
	private void giveTwoCards(List<Client> players) throws Exception{
		match.giveCardsForPlayers();
		for(Client c:players){
			broadcaster.broadcast("@CARD:"+c.getHand()[0],c);
			broadcaster.broadcast("@CARD:"+c.getHand()[1],c);
		}
	}
}
