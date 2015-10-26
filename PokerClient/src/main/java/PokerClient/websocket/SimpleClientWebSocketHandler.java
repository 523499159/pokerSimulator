package PokerClient.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import PokerClient.controller.MainWindowController;


public class SimpleClientWebSocketHandler extends TextWebSocketHandler {



    private WebSocketSession session;
    @Autowired
    MainWindowController controller;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    	controller.serverResposne(message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    public WebSocketSession getSession() {
        return session;
    }

}