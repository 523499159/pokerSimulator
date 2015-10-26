package pokerServer.messageConverter;

import org.springframework.web.socket.TextMessage;
import pokerServer.messages.Message;
public interface MessageConverter {

	Message convert(TextMessage te);
}
