package PokerClient.message;

import PokerClient.Card.Card;

public class CardMessage extends Message{
	private Card card;

	public CardMessage(Card c) {
		super(MessageType.CARD);
			card=c;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}





}
