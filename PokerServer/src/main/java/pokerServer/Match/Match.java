package pokerServer.Match;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pokerServer.Client.Client;
import pokerServer.Deck.Deck;
import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Card.Rank;
import scala.annotation.meta.getter;
@Component
public class Match implements TableOperator {
	
	
	private List<Card> cards;
	private List<Client> players;
	private double smallBlind;
	private double bigBlind;
	private Client winner;
	private Client dealer;
	private double moneyOnTable;
	
	@Autowired
	Deck deck;
	
	
	
	private Match() {
		super();
	}
	@Override
	public void initializeNewMatch(List<Client> players){

	inititalizeBlinds();
	initlizeDeck();
	this.players=players;
	dealer=chooseDealer();

	}

	private void initlizeDeck(){
		cards=deck.getRandomizedCards();
	}
	

	private double inititalizeBlinds() {
		smallBlind= ThreadLocalRandom.current().nextDouble(1,100 + 1);
		bigBlind=smallBlind*2;
		return 0;
	}
	@Override
	public void putCardsToTable(int numberCardsToPut) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Client chooseDealer() {
		int random=ThreadLocalRandom.current().nextInt(0, players.size()+1);
		return players.get(random);
	}
	@Override
	public void giveCardsForPlayers() {
		int amountGivenCards=2;
		for(int i=0;i<amountGivenCards;i++){
			for(Client c:players){
				c.getHand()[i]=getRandomCardFromCurrentDeck();
			}
		}
		
		
	}
	public List<Card> getCards() {
		return cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public List<Client> getPlayers() {
		return players;
	}
	public void setPlayers(List<Client> players) {
		this.players = players;
	}
	public double getSmallBlind() {
		return smallBlind;
	}
	public void setSmallBlind(double smallBlind) {
		this.smallBlind = smallBlind;
	}
	public double getBigBlind() {
		return bigBlind;
	}
	public void setBigBlind(double bigBlind) {
		this.bigBlind = bigBlind;
	}
	public Client getWinner() {
		return winner;
	}
	public void setWinner(Client winner) {
		this.winner = winner;
	}
	public Client getDealer() {
		return dealer;
	}
	public void setDealer(Client dealer) {
		this.dealer = dealer;
	}

	public Card getRandomCardFromCurrentDeck(){
		int random=ThreadLocalRandom.current().nextInt(0, cards.size()+1);
		Card c= cards.remove(random);
		return c;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
