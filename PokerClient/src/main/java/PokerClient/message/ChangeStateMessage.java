package PokerClient.message;

public class ChangeStateMessage extends Message {

		private State state;


		public ChangeStateMessage(State state) {
			super(MessageType.CHANGE_STATE);
			this.state = state;
		}

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}


}
