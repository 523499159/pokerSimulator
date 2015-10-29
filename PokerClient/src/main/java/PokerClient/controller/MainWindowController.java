package PokerClient.controller;


import java.io.IOException;

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
public class MainWindowController  {
    private static final Logger LOG = LoggerFactory
            .getLogger(MainWindowController.class);

    private MainWindowService mainWindowService;

	@FXML Button startButton;

	@FXML TextField nameField;

    @Autowired
    SimpleClientWebSocketHandler handler;

	@FXML TextArea serverResponseView;


	@FXML Label smallBlindLabel;

	@FXML Label bigBlindLabel;

	@FXML ListView<String> handCards;

	@FXML TextField raiseValue;

	@FXML ListView<String> tableOfCards;

	@FXML ListView<String> playersAtMatch;

	@FXML Label moneyWallet;

	@FXML Label moneyOnTable;

	@FXML Label alreadyPut;

    @Autowired
    MainWindowController(MainWindowService mainWindowService) {

        this.mainWindowService = mainWindowService;


    }


@FXML
public void initialize(){
    smallBlindLabel.setText("0.00");
    bigBlindLabel.setText("0.00");
}


	@FXML public void connectAndIntroduce(ActionEvent event) {
	     try {
			handler.getSession().sendMessage(new TextMessage("@INTRODUCE:"+nameField.getText()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void serverResposne(String text){
		String s=serverResponseView.getText();
		serverResponseView.setText(s+"\n"+text);
	}


	public void addCardToHand(Card c){
		handCards.getItems().add(c.toString());

	}

	public void setSmallBlind(Double val){
		smallBlindLabel.setText(val+"");
	}

	public void setBigBlind(Double val){
		bigBlindLabel.setText(val+"");
	}

	public void setMoneyWallet(Double val){
		moneyWallet.setText(val+"");
	}

	@FXML public void passAction(ActionEvent event) {
		 sendMsgToServer("@DECISION:@PASS");

	}


	@FXML public void checkAction(ActionEvent event) {

	double moneytoChek=2;

			 sendMsgToServer("@DECISION:@CHECK:"+moneytoChek);

	}


	@FXML public void raiseAction(ActionEvent event) {

		double raisedValue=50;
		sendMsgToServer("@DECISION:@RAISE:"+raisedValue);


}


	private void sendMsgToServer(String s){
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


	public void addPlayerToMatch(String name){
		playersAtMatch.getItems().add(name);
	}
}
