package PokerClient.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import PokerClient.controller.MainWindowController;
import PokerClient.messageConverter.MessageConverter;
import PokerClient.messageHandler.MessageHandler;


public class SimpleClientWebSocketHandler extends TextWebSocketHandler {



    private WebSocketSession session;

    @Autowired
    MessageConverter converter;

    @Autowired
    MessageHandler handler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	handler.handleMessage(converter.convert(message));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    public WebSocketSession getSession() {
        return session;
    }

}