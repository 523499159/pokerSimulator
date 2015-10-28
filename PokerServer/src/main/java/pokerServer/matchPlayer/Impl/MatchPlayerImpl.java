package pokerServer.matchPlayer.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pokerServer.Client.Client;
import pokerServer.Deck.Card.Card;
import pokerServer.Match.Match;
import pokerServer.broadcaster.Broadcast;
import pokerServer.matchPlayer.MatchPlayer;
import pokerServer.messages.DecisionMessage;
import pokerServer.messages.Message;

@Service
public class MatchPlayerImpl implements MatchPlayer{

	@Autowired
	Broadcast broadcaster;
	
	@Autowired
	Match match;
	
	private int currentDealerIdx;
	private int smallBlindIdx;
	private int bigBlindIdx;
	private List<Message> roundResponses;
	private List<Card> table;
	
	
	@Override
	public void playMatch(List<Client> players) throws Exception {
		roundResponses=new ArrayList<Message>();
		table =new ArrayList<Card>();
		
		System.out.println("Play Match");
		match.initializeNewMatch(players);
		System.out.println("INIT");
		preRound(players);
		System.out.println("AFTER PRE");
		giveTwoCards(players);
		System.out.println("AFTER GIVE TWO");
		startLicitation(players);
		System.out.println("AFTER first licitation");
		turnCard(3, players);
		System.out.println("AFTER FLOP");
	
	
	}

	
	private void preRound(List<Client> players) throws Exception{
		broadcaster.broadcast("Rozpocznamy mecz", players);	
		broadcaster.broadcast("@SMALLBLIND:"+match.getSmallBlind(), players);		
		broadcaster.broadcast("@BIGBLIND: "+match.getBigBlind(), players);		
		Client dealer=match.getDealer();		
		setIndexes(players.indexOf(dealer), players);			
	}
	
	private void setIndexes(int dealer, List<Client> players) throws Exception{
		System.out.println("SendIndexes");
		currentDealerIdx=dealer;
		broadcaster.broadcast("Dealerem jest: "+players.get(currentDealerIdx).getName(), players);	
		smallBlindIdx=(currentDealerIdx+1)%(players.size());
		bigBlindIdx=(currentDealerIdx+2)%(players.size());
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
	
	private Boolean checkResponses(List<Client> players){
		Boolean responsesCheckMinimum=roundResponses.size()>=players.size();
		Boolean equality=checkEqualityMoneyPutOnTable(players);
		System.out.println(responsesCheckMinimum+"  "+equality);
		return responsesCheckMinimum&&equality;
	}
	

	
	private void clientPass(Client c){

		
	}
	
	private Boolean checkEqualityMoneyPutOnTable(List<Client> players){
		return players.stream()
				.map(p->p.getMoneyPutInSingnleRound())
				.filter(x->x>0).distinct()
				.collect(Collectors.toList())
				.size()==1;
		
		
	}
	
	private void turnCard(int cardsToTurn,List<Client> players ) throws Exception{
		match.getRandomCardFromCurrentDeck();
		for(int i=0;i<cardsToTurn;i++){
		
			Card c=match.getRandomCardFromCurrentDeck();
			table.add(c);
			System.out.println("Turn "+table.size()+" card");
			notifyPlayersAboutTableCards(c, players);
		}
	}
	
	private void notifyPlayersAboutTableCards(Card c, List<Client> players) throws Exception{
		broadcaster.broadcast("@TABLE:@CARD:"+c, players);		
	}
	
	private Client nextClient(Client c, List<Client> players){
		int nextIDX=(players.indexOf(c)+1)%(players.size());
		return players.get(nextIDX);
	}
	
	private void notifyAndWaitForTurn(Client c, List<Client> players) throws Exception{
		broadcaster.broadcast("@YOURTURN", c);
		broadcaster.broadcast("Czekaj, teraz gracz: "+c.getName()+" wykonuje ruch", players, c);
		Boolean keepWaiting=true;
		do{
			Thread.sleep(2000);
			System.out.println("waiting for resposne from : "+c.getName()+"  "+roundResponses.size());
			
			if(!roundResponses.isEmpty()){
				
				if((roundResponses.get(roundResponses.size()-1).getSession()).equals(c.getSession())){
					keepWaiting=false;
				}
			}
		}while(keepWaiting);

			
		
	}
	
	private void startLicitation(List<Client> players) throws Exception{
		Client underTheGun=nextClient(players.get(bigBlindIdx), players);
		notifyAndWaitForTurn(underTheGun,players);
	}
	
	@Override
	public void addDecision(DecisionMessage msg){
		
		roundResponses.add(msg);
	}
	
	
}
