package pokerServer.messages;

public class IntroduceMessage extends Message {
	
	String name;

	public IntroduceMessage(String name) {
		super(TypeMessage.INTRODUCE);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
