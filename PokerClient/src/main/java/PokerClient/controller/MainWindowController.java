package PokerClient.controller;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import PokerClient.service.MainWindowService;
import PokerClient.websocket.SimpleClientWebSocketHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;



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

    @Autowired
    MainWindowController(MainWindowService mainWindowService) {

        this.mainWindowService = mainWindowService;
    }



	@FXML public void connectAndIntroduce(ActionEvent event) {

	     try {
			handler.getSession().sendMessage(new TextMessage(nameField.getText()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void serverResposne(String text){
		String s=serverResponseView.getText();
		serverResponseView.setText(s+"\n"+text);
	}






}
