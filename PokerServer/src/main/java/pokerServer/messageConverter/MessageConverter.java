package pokerServer.messageConverter;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import pokerServer.messages.Message;


public interface MessageConverter {

	Message convert(TextMessage te, WebSocketSession s);
}
