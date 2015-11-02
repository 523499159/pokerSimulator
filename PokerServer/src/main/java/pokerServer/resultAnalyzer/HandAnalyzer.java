package pokerServer.resultAnalyzer;

import java.util.List;

import pokerServer.Client.Client;
import pokerServer.Deck.Card.Card;

public interface HandAnalyzer {


Client whichClientWin(List<Client> player, Card[] table) throws Exception;

	
}
