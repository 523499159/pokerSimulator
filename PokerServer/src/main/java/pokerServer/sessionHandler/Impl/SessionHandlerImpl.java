package pokerServer.sessionHandler.Impl;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pokerServer.Client.Client;
import pokerServer.sessionHandler.SessionHandler;

@Service
public class SessionHandlerImpl  implements SessionHandler{
	
	
    private final ConcurrentHashMap<String, Client> currentClients = new ConcurrentHashMap<>();	
    private String lastBroadcastCommunicate;
    
    @Value("${tick.interval.max:5000}")
    private Integer MAX_TICK_INTERVAL;

    @Value("${tick.interval.least:1000}")
    private Integer LEAST_TICK_INTERVAL;
    
    private ScheduledFuture<?> future;
    
    @Override
    public synchronized void addSession( Client client) throws Exception {
        if (currentClients.isEmpty()) {
            start();
        }
        currentClients.put(client.getSession().getId(), client);
    }
    
    @Override
    public synchronized void removeSession(String id) {
        currentClients.remove(id);
        if (currentClients.isEmpty()) {
            stop();
        }
    }
    @Override
    public Client getClientFromSession(WebSocketSession s){
    	return currentClients.get(s.getId());
    }
    

    @Override
    public void broadcast(String message) throws Exception {
    	
    	if(lastBroadcastCommunicate!=message){
    		
	        TextMessage textMessage = new TextMessage(message);
	        for (ConcurrentHashMap.Entry<String, Client> client : currentClients.entrySet()) {
	            client.getValue().getSession().sendMessage(textMessage);
	        }
	        
        lastBroadcastCommunicate=message;
    	}
    }

    private void start() throws Exception {
        Random rand = new Random();
       
        future = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            try {
                int delay = rand.nextInt(MAX_TICK_INTERVAL - LEAST_TICK_INTERVAL);
                Thread.sleep(delay);
                
                waitingMoment();
         
      

            } catch (Exception e) {
   
            }
        }, 0, LEAST_TICK_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private void stop() {
        future.cancel(false);
    }
    
    private void waitingMoment() throws Exception{
    	int readyPlayers=numberOfReadyPlayers();
    	if(readyPlayers==1){
    		broadcast("Musisz poczekac na innech graczy.");
    		
    	}
    	if(readyPlayers>=2){
    		broadcast("Mozemy zaczynac.");
    		Thread.sleep(3000);
    	}

    }
    
    private int numberOfReadyPlayers(){
   return currentClients.values().stream().
		   filter(client->client.getReadyForPlay().equals(true)).
		   collect(Collectors.toList()).size();
    }
}
