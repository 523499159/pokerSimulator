package PokerClient.messageHandler.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PokerClient.controller.MainWindowController;
import PokerClient.message.BlindType;
import PokerClient.message.BlindValueMessage;
import PokerClient.message.CardMessage;
import PokerClient.message.CardPlace;
import PokerClient.message.Message;
import PokerClient.message.SimpleMessage;
import PokerClient.messageHandler.MessageHandler;
import javafx.application.Platform;

@Service
public class MessageHandlerImpl implements MessageHandler {

	@Autowired
	MainWindowController controller;

	@Override
	public void handleMessage(Message message) {

		if (message instanceof SimpleMessage) {
			SimpleMessage simple = (SimpleMessage) message;
			handleSimple(simple);
		}

		if (message instanceof CardMessage) {
			CardMessage card = (CardMessage) message;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					handleCard(card);
				}
			});
		}

		if (message instanceof BlindValueMessage) {
			BlindValueMessage msg = (BlindValueMessage) message;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					handleBlind(msg);
				}
			});

		}

	}

	private void handleCard(CardMessage card) {

		if(card.getPlace().equals(CardPlace.HAND)){
		controller.addCardToHand(card.getCard());

		}

		if(card.getPlace().equals(CardPlace.TABLE)){
			controller.addCardToTable(card.getCard());

			}

	}

	private void handleBlind(BlindValueMessage msg) {

		if (msg.getTypeBlind().equals(BlindType.BIG)) {
			controller.setBigBlind(msg.getValue());
		}

		if (msg.getTypeBlind().equals(BlindType.SMALL)) {
			controller.setSmallBlind(msg.getValue());
		}

	}

	private void handleSimple(SimpleMessage simp) {
		controller.serverResposne(simp.getText());

	}

}
