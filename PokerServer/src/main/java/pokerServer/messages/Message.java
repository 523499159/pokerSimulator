package pokerServer.messages;

import org.springframework.web.socket.WebSocketSession;

public abstract class Message {
	
	TypeMessage type;
	WebSocketSession session;
	


	public Message(TypeMessage type, WebSocketSession s) {
		super();
		this.type = type;
		this.session= s;
	}

	
	public WebSocketSession getSession() {
		return session;
	}


	public void setSession(WebSocketSession session) {
		this.session = session;
	}


	public Message(TypeMessage type) {
		super();
		this.type = type;
	}

	public TypeMessage getType() {
		return type;
	}

	public void setType(TypeMessage type) {
		this.type = type;
	}
	
	

}
