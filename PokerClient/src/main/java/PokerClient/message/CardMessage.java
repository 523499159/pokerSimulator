package PokerClient.message;

import PokerClient.Card.Card;

public class CardMessage extends Message{
	private Card card;
	private CardPlace place;
	public CardMessage(Card c, CardPlace p) {
		super(MessageType.CARD);
			card=c;
			place=p;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public CardPlace getPlace() {
		return place;
	}

	public void setPlace(CardPlace place) {
		this.place = place;
	}





}
