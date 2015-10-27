package PokerClient.message;

public abstract class Message {

	MessageType type;




	public Message(MessageType type) {
		super();
		this.type = type;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}



}
