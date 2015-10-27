package pokerServer.matchPlayer;

import java.util.List;

import pokerServer.Client.Client;

public interface MatchPlayer {
	
void playMatch(List<Client> players) throws Exception;

}
