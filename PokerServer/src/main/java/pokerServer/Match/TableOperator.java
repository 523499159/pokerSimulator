package pokerServer.Match;

import java.util.List;

import pokerServer.Client.Client;

public interface TableOperator {
	

	void putCardsToTable(int numberCardsToPut);
	Client chooseDealer();
	void giveCardsForPlayers();
	void initializeNewMatch(List<Client> players);
	
}
