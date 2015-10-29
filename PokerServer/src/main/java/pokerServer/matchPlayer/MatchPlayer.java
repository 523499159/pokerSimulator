package pokerServer.matchPlayer;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import pokerServer.Client.Client;
import pokerServer.messages.DecisionMessage;

public interface MatchPlayer {
	
void playMatch(List<Client> players) throws Exception;

void addDecision(DecisionMessage msg);

void clientPass(WebSocketSession s);

Client getPlayerFromSession(WebSocketSession s);

}
