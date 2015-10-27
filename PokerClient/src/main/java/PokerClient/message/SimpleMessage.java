package PokerClient.message;

public class SimpleMessage  extends Message{


	String text;

	public SimpleMessage(String s) {
		super(MessageType.SIMPLE);
		text=s;

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}




}
