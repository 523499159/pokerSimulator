package PokerClient.message;

public class PlayerMessage extends Message {
    String playerName;


	public PlayerMessage(String playerName) {
		super(MessageType.PLAYER);
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


}
