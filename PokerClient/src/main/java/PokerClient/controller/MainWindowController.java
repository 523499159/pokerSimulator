package PokerClient.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import PokerClient.Card.Card;
import PokerClient.service.MainWindowService;
import PokerClient.websocket.SimpleClientWebSocketHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

@Component
public class MainWindowController {
	private static final Logger LOG = LoggerFactory.getLogger(MainWindowController.class);

	private MainWindowService mainWindowService;

	@FXML
	Button startButton;

	@FXML
	TextField nameField;

	@Autowired
	SimpleClientWebSocketHandler handler;

	@FXML
	TextArea serverResponseView;

	@FXML
	Label smallBlindLabel;

	@FXML
	Label bigBlindLabel;

	@FXML
	ListView<String> handCards;

	@FXML
	TextField raiseValue;
	@FXML
	TextField chackValue;

	@FXML
	ListView<String> tableOfCards;

	@FXML
	ListView<String> playersAtMatch;

	@FXML
	Label moneyWallet;

	@FXML
	Label moneyOnTable;

	@FXML
	Label alreadyPut;

	private double maxput;
	private double startMoney;
	private double alreadyPutValue;



	@Autowired
	MainWindowController(MainWindowService mainWindowService) {

		this.mainWindowService = mainWindowService;

	}

	@FXML
	public void initialize() {
		smallBlindLabel.setText("0.00");
		bigBlindLabel.setText("0.00");
		alreadyPutValue = 0;
	}

	@FXML
	public void connectAndIntroduce(ActionEvent event) {
		try {
			handler.getSession().sendMessage(new TextMessage("@INTRODUCE:" + nameField.getText()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void serverResposne(String text) {
		String s = serverResponseView.getText();
		serverResponseView.setText(s + "\n" + text);
	}

	public void addCardToHand(Card c) {
		handCards.getItems().add(c.toString());

	}

	public void setSmallBlind(Double val) {
		smallBlindLabel.setText(val + "");
	}

	public void setBigBlind(Double val) {
		bigBlindLabel.setText(val + "");
	}

	public void setMoneyWallet(Double val) {

		alreadyPutValue = startMoney - val;
		alreadyPut.setText(alreadyPutValue + "");
		moneyWallet.setText(val + "");

	}

	public void setMoneyTable(Double val) {
		moneyOnTable.setText(val + "");

	}

	public void setMaxPut(Double val) {
		setMaxput(val);
	}

	@FXML
	public void passAction(ActionEvent event) {
		sendMsgToServer("@DECISION:@PASS");

	}

	@FXML
	public void checkAction(ActionEvent event) {
			double moneyNeedToCheck=maxput-alreadyPutValue;
		sendMsgToServer("@DECISION:@CHECK:" + 2);

	}

	@FXML
	public void raiseAction(ActionEvent event) {

		double raisedValue = 50;
		sendMsgToServer("@DECISION:@RAISE:" + raisedValue);

	}

	public Double getCurrentMoneyInWallet() {
		return Double.parseDouble(moneyWallet.getText());
	}

	private void sendMsgToServer(String s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					handler.getSession().sendMessage(new TextMessage(s));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public void addCardToTable(Card card) {
		tableOfCards.getItems().add(card.toString());

	}

	public void addPlayerToMatch(String name) {
		playersAtMatch.getItems().add(name);
	}

	public double getMaxput() {
		return maxput;
	}

	public void setMaxput(double maxput) {
		this.maxput = maxput;
		double moneyNeedToCheck=maxput-alreadyPutValue;
		chackValue.setText(moneyNeedToCheck+"");
		raiseValue.setText((moneyNeedToCheck+(Double.parseDouble(bigBlindLabel.getText())/2))+"");

	}

	public double getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(double startMoney) {
		moneyWallet.setText(startMoney + "");
		this.startMoney = startMoney;
	}
}
