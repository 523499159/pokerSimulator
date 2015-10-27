package pokerServer.sessionHandler.Impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.tomcat.util.collections.ManagedConcurrentWeakHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pokerServer.Client.Client;
import pokerServer.broadcaster.Broadcast;
import pokerServer.matchPlayer.MatchPlayer;
import pokerServer.sessionHandler.SessionHandler;

@Service
public class SessionHandlerImpl implements SessionHandler {

	private final ConcurrentHashMap<String, Client> currentClients = new ConcurrentHashMap<>();
	private String lastBroadcastCommunicate;

	@Value("${tick.interval.max:5000}")
	private Integer MAX_TICK_INTERVAL;

	@Value("${tick.interval.least:1000}")
	private Integer LEAST_TICK_INTERVAL;

	private ScheduledFuture<?> future;

	@Autowired
	MatchPlayer matchPlayer;

	@Autowired
	Broadcast broadcaster;
	private boolean matchStarted;

	@Override
	public synchronized void addClient(Client client) throws Exception {
		if (currentClients.isEmpty()) {
			start();
		}
		currentClients.put(client.getSession().getId(), client);
	}

	@Override
	public synchronized void removeClient(String id) {
		currentClients.remove(id);
		if (currentClients.isEmpty()) {
			stop();
		}
	}

	@Override
	public Client getClientFromSession(WebSocketSession s) {
		return currentClients.get(s.getId());
	}

	private void start() throws Exception {
		Random rand = new Random();
		matchStarted=false;
		future = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
			try {
				int delay = rand.nextInt(MAX_TICK_INTERVAL - LEAST_TICK_INTERVAL);
				Thread.sleep(delay);
			
				waitingMoment();

			} catch (Exception e) {

			}
		} , 0, LEAST_TICK_INTERVAL, TimeUnit.MILLISECONDS);
	}

	private void stop() {
		future.cancel(false);
	}

	private void waitingMoment() throws Exception {
		List<Client> readyPlayers = getReadyPlayers();
		if (readyPlayers.size() == 1) {
		broadcast("Musisz poczekac na innech graczy.", readyPlayers);

		}
		if (readyPlayers.size() >= 2 && !matchStarted) {
		broadcast("Mozemy zaczynac.", readyPlayers);
		Thread.sleep(5000);
			matchStarted=true;
			matchPlayer.playMatch(readyPlayers);
		
		}

	}

	private List<Client> getReadyPlayers() {
		return currentClients.values().stream().filter(client -> client.getReadyForPlay().equals(true))
				.collect(Collectors.toList());
	}

	private void broadcast(String s, List<Client> c) {
		if (lastBroadcastCommunicate != s) {
			try {
				broadcaster.broadcast(s, c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastBroadcastCommunicate = s;

	}
}
