package pokerServer.broadcaster;

import java.util.List;

import pokerServer.Client.Client;

public interface Broadcast {
	
	void broadcast(String message, List<Client> reciver) throws Exception;
	void broadcast(String message, Client c) throws Exception;
	void broadcast(String message, List<Client> reciver, Client except) throws Exception;

}
