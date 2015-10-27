package PokerClient.messageHandler.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PokerClient.controller.MainWindowController;
import PokerClient.message.CardMessage;
import PokerClient.message.Message;
import PokerClient.message.SimpleMessage;
import PokerClient.messageHandler.MessageHandler;



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
		handleCard(card);
		}


	}

		private void handleCard(CardMessage card) {
			controller.addCardToHand(card.getCard());

	}

		private void handleSimple(SimpleMessage simp){
			controller.serverResposne(simp.getText());

		}









}
