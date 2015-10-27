package pokerServer.sessionHandler;

import org.springframework.web.socket.WebSocketSession;

import pokerServer.Client.Client;

public interface SessionHandler {

	void addSession(Client client) throws Exception;

	void removeSession(String id);

	void broadcast(String message) throws Exception;

	Client getClientFromSession(WebSocketSession s);

}
