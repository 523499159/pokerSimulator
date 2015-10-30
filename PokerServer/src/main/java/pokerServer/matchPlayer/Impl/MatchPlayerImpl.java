package pokerServer.matchPlayer.Impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
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
	private List<Client> players;
	private double currentPot;
	
	
	@Override
	public void playMatch(List<Client> players) throws Exception {
		roundResponses=new ArrayList<Message>();
		
		table =new ArrayList<Card>();
		this.players=players;
		initPlayersWallets(match);
		System.out.println("Play Match");
		match.initializeNewMatch(players);
		currentPot=0;
		System.out.println("INIT");
		notifyAboutOtherPlayers();
		preRound();
		
		getBlindsFromClient(smallBlindIdx, bigBlindIdx);
		System.out.println("AFTER PRE");
		giveTwoCards();
		System.out.println("AFTER GIVE TWO");
		
		startLicitation(players.get(bigBlindIdx));
		System.out.println("AFTER first licitation");
		turnCard(3);
		System.out.println("AFTER FLOP");
		
		startLicitation(players.get(currentDealerIdx));
		turnCard(1);
		System.out.println("AFTER ROUND 3");
		
		startLicitation(players.get(currentDealerIdx));
		turnCard(1);
		System.out.println("AFTER ROUND 4");
		
		startLicitation(players.get(currentDealerIdx));
		System.out.println("AFTER LAST LICITATION");
		
	}

	private void notifyAboutOtherPlayers() throws Exception{
		for(Client c:players){
			broadcaster.broadcast("@PLAYER:"+c.getName(), players,c);		
		}
	}
	private void preRound() throws Exception{
		broadcaster.broadcast("Rozpocznamy mecz", players);	
		broadcaster.broadcast("@MONEY:@SMALLBLIND:"+match.getSmallBlind(), players);		
		broadcaster.broadcast("@MONEY:@BIGBLIND: "+match.getBigBlind(), players);	
	
		Client dealer=match.getDealer();		
		setIndexes(players.indexOf(dealer), players);			
	}
	private void initPlayersWallets(Match m) throws Exception{
		for(Client c:players){
			c.setMoney(m.getMoneyOnStart());
			broadcaster.broadcast("@MONEY:@WALLET_START:"+c.getMoney(), c);
		}
		
	}
	
	private void setIndexes(int dealer, List<Client> players) throws Exception{
		currentDealerIdx=dealer;
		broadcaster.broadcast("Dealerem jest: "+players.get(currentDealerIdx).getName(), players);	
		smallBlindIdx=(currentDealerIdx+1)%(players.size());
		bigBlindIdx=(currentDealerIdx+2)%(players.size());
		broadcaster.broadcast(players.get(smallBlindIdx).getName()+" placi mala ciemna "+match.getSmallBlind(), players);
		broadcaster.broadcast(players.get(bigBlindIdx).getName()+" placi duza ciemna "+match.getBigBlind(), players);
	}
	
	private void getBlindsFromClient(int smallBlindIdx,int bigBlindIdx) throws Exception{
		
		Client onSmall=players.get(smallBlindIdx);
		double sm=onSmall.getMoney()-match.getSmallBlind();
		onSmall.setMoney(sm);
		
		Client onBig=players.get(bigBlindIdx);
		double bg=onBig.getMoney()-match.getBigBlind();
		onBig.setMoney(bg);
		
		increasePot(match.getSmallBlind());
		increasePot(match.getBigBlind());
		
		broadcaster.broadcast("@MONEY:@WALLET:"+onSmall.getMoney(), onSmall);
		broadcaster.broadcast("@MONEY:@WALLET:"+onBig.getMoney(), onBig);
		
		onSmall.addToMoneyPutedInRound(match.getSmallBlind());
		onBig.addToMoneyPutedInRound(match.getBigBlind());
		
		broadcaster.broadcast("@TABLE:@MONEY:"+(currentPot), players);
	}
	
	private void getMaxPutOnTable() throws Exception{
		
		Client maxPut = players.stream()
                 .max((p1, p2) -> Double.compare(p1.getMoneyPutInSingnleRound(), p2.getMoneyPutInSingnleRound()))
                 .get();
		broadcaster.broadcast("@MONEY:@CURRENT_BET:"+maxPut.getMoneyPutInSingnleRound(), players);
	}
	
	private void giveTwoCards() throws Exception{
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
	

	@Override
	public void clientPass(WebSocketSession s){
	Client pass=getPlayerFromSession(s);
		
		players.remove(pass);
		
		if(players.size()==1){
			winnerIs(players.get(0));
		}

		
	}
	
	
	
	private Boolean checkEqualityMoneyPutOnTable(List<Client> players){
	Boolean res=true;
	for(int i=1;i<players.size();i++){
		if(players.get(i).getMoneyPutInSingnleRound()>0 &&players.get(i-1).getMoneyPutInSingnleRound()>0){
			System.out.println(players.get(i).getMoneyPutInSingnleRound()+"   "+players.get(i-1).getMoneyPutInSingnleRound());
			if(players.get(i).getMoneyPutInSingnleRound()!=players.get(i-1).getMoneyPutInSingnleRound()){
				res=false;
			}
		}
	}		
	return res;
	}
	private void increasePot(Double d){
		currentPot=currentPot+d;
	}
	private void turnCard(int cardsToTurn) throws Exception{
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
		broadcaster.broadcast("Teraz Twoja kolej na ruch", c);
		broadcaster.broadcast("@CHANGE_STATE:@ENABLE", c);
		broadcaster.broadcast("Czekaj, teraz gracz: "+c.getName()+" wykonuje ruch", players, c);
		broadcaster.broadcast("@CHANGE_STATE:@DISABLE", players, c);
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
	
	private void startLicitation(Client start) throws Exception{
		Client activ=start;
		int idx=0;
		Boolean cond;
		do{
			for(int i=0;i<players.size();i++){
				idx=players.indexOf(activ);
				activ=nextClient(players.get(idx), players);
				getMaxPutOnTable();
				notifyAndWaitForTurn(activ, players);
			}
			cond=checkResponses(players);
		}
		while(!cond);

	}
	
	@Override
	public void addDecision(DecisionMessage msg){
		
		roundResponses.add(msg);
	}
	
	private void winnerIs(Client c){
		
	}
	@Override
	public Client getPlayerFromSession(WebSocketSession s){
return players.stream().filter(p->p.getSession().equals(s)).findFirst().get();
	}
}
