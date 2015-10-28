package pokerServer.broadcaster.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import pokerServer.Client.Client;
import pokerServer.broadcaster.Broadcast;
@Service
public class BroadcastImpl implements Broadcast {

	


	@Override
	public void broadcast(String message, List<Client> reciver) throws Exception {
    
	        TextMessage textMessage = new TextMessage(message);
	        for (Client client : reciver) {
	            client.getSession().sendMessage(textMessage);
	        }
	        
 
    	}

	@Override
	public void broadcast(String message, Client c) throws Exception {
        TextMessage textMessage = new TextMessage(message);
        c.getSession().sendMessage(textMessage);
  
	}

	@Override
	public void broadcast(String message, List<Client> reciver, Client except) throws Exception {
	      TextMessage textMessage = new TextMessage(message);
	        for (Client client : reciver) {
	        	if(!client.getSession().equals(except.getSession())){
	            client.getSession().sendMessage(textMessage);
	        	}
	        }
	}
	
}
