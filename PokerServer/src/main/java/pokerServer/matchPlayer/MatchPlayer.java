package pokerServer.matchPlayer;

import java.util.List;

import pokerServer.Client.Client;
import pokerServer.messages.DecisionMessage;

public interface MatchPlayer {
	
void playMatch(List<Client> players) throws Exception;

void addDecision(DecisionMessage msg);

}
