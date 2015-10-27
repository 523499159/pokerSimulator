package pokerServer.webSocketHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import pokerServer.Client.Client;
import pokerServer.broadcaster.Broadcast;
import pokerServer.messageConverter.MessageConverter;
import pokerServer.messageHandler.MessageHandler;
import pokerServer.sessionHandler.SessionHandler;

@Service
public class SimpleServerWebSocketHandler extends TextWebSocketHandler  {

    @Autowired
    private SessionHandler sessionHandler;
    
    @Autowired
    private MessageHandler messageHandler;
    
    @Autowired
    private MessageConverter messageConverter;
    

    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	sessionHandler.addClient(new Client(session));
    
    	
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if (!payload.isEmpty()) {
        	messageHandler.handleMessage(messageConverter.convert(message,session));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    	sessionHandler.removeClient(session.getId());
    }
}
