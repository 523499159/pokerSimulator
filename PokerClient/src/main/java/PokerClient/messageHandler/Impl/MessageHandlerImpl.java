package PokerClient.messageHandler.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PokerClient.controller.MainWindowController;
import PokerClient.message.MoneyMessageType;
import PokerClient.message.MoneyMessage;
import PokerClient.message.CardMessage;
import PokerClient.message.CardPlace;
import PokerClient.message.Message;
import PokerClient.message.PlayerMessage;
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

		if (message instanceof PlayerMessage) {
			PlayerMessage msg = (PlayerMessage) message;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					handlePlayer(msg);
				}
			});
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

		if (message instanceof MoneyMessage) {
			MoneyMessage msg = (MoneyMessage) message;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					handleMoney(msg);
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

	private void handleMoney(MoneyMessage msg) {

		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.BIG)) {
			controller.setBigBlind(msg.getValue());
		}

		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.SMALL)) {
			controller.setSmallBlind(msg.getValue());
		}

		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.WALLET)){
			controller.setMoneyWallet(msg.getValue());
		}
		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.TABLE)){
			controller.setMoneyTable(msg.getValue());
		}

		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.WALLET_START)){
			controller.setStartMoney(msg.getValue());
		}

		if (msg.getTypeMoneyMSG().equals(MoneyMessageType.CURRENT_BET)){
			controller.setMaxPut(msg.getValue());
		}


	}

	private void handleSimple(SimpleMessage simp) {
		controller.serverResposne(simp.getText());

	}
	private void handlePlayer(PlayerMessage msg) {
		controller.addPlayerToMatch(msg.getPlayerName());

	}

}
