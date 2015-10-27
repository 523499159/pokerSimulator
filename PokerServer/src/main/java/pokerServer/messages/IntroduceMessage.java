package pokerServer.messages;

import org.springframework.web.socket.WebSocketSession;

public class IntroduceMessage extends Message {
	
	String name;

	public IntroduceMessage(String name, WebSocketSession s) {
		super(TypeMessage.INTRODUCE, s);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
