package pokerServer.Client;

import org.springframework.web.socket.WebSocketSession;

import pokerServer.Deck.Hand;
import pokerServer.Deck.Card.Card;

public class Client implements Comparable<Client> {
	
	private String name;
	private WebSocketSession session;
	private Boolean readyForPlay;
	private Double money;
	private Card[] holeCards;
	private double moneyPutInSingnleRound;
	private Hand bestCombinationCard;
	
	
	
	public Client(WebSocketSession session) {
		super();
		this.session = session;
		readyForPlay=false;
		holeCards=new Card[2];
	}
	
	
	public Client(String name, WebSocketSession session) {
		super();
		this.name = name;
		this.session = session;
		readyForPlay=false;
	}
	
	


	public Hand getBestCombinationCard() {
		return bestCombinationCard;
	}


	public void setBestCombinationCard(Hand bestCombinationCard) {
		this.bestCombinationCard = bestCombinationCard;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public Card[] getHand() {
		return holeCards;
	}


	public void setHand(Card[] hand) {
		this.holeCards = hand;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public Boolean getReadyForPlay() {
		return readyForPlay;
	}
	public void setReadyForPlay(Boolean readyForPlay) {
		this.readyForPlay = readyForPlay;
	}
	
	public void introduceAndReadyForPlay(String name){
		this.name=name;
		readyForPlay=true;
	}
	
	public void clearMoneyPutedInRound(){
		moneyPutInSingnleRound=0;
	}
	public void addToMoneyPutedInRound(Double d){
		money=money-d;
		moneyPutInSingnleRound=moneyPutInSingnleRound+d;
	}

	public double getMoneyPutInSingnleRound() {
		return moneyPutInSingnleRound;
	}


	public void setMoneyPutInSingnleRound(double moneyPutInSingnleRound) {
		this.moneyPutInSingnleRound = moneyPutInSingnleRound;
	}


	@Override
	public int compareTo(Client o) {

		return this.getBestCombinationCard().compareTo(o.bestCombinationCard);
	}
	

}
