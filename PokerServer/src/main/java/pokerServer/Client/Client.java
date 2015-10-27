package pokerServer.Client;

import org.springframework.web.socket.WebSocketSession;

public class Client {
	
	private String name;
	private WebSocketSession session;
	private Boolean readyForPlay;
	
	
	
	public Client(WebSocketSession session) {
		super();
		this.session = session;
		readyForPlay=false;
	}
	
	
	public Client(String name, WebSocketSession session) {
		super();
		this.name = name;
		this.session = session;
		readyForPlay=false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public Boolean getReadyForPlay() {
		return readyForPlay;
	}
	public void setReadyForPlay(Boolean readyForPlay) {
		this.readyForPlay = readyForPlay;
	}
	
	public void introduceAndReadyForPlay(String name){
		this.name=name;
		readyForPlay=true;
	}
	
	

}
