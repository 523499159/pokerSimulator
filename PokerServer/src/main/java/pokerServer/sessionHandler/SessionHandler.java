package pokerServer.sessionHandler;

import org.springframework.web.socket.WebSocketSession;

import pokerServer.Client.Client;

public interface SessionHandler {

	void addClient(Client client) throws Exception;

	void removeClient(String id);

	Client getClientFromSession(WebSocketSession s);

}
