package pokerServer.messages;

public abstract class Message {
	
	TypeMessage type;

	
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
