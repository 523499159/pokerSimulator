package PokerClient.messageConverter;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import PokerClient.message.Message;



public interface MessageConverter {

	Message convert(TextMessage te);
}
