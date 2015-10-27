package PokerClient.message;

public class BlindValueMessage extends Message {
			private BlindType typeBlind;
			double value;



			public BlindValueMessage(BlindType type, double value) {
				super(MessageType.BLIND_VALUE);
				this.typeBlind = type;
				this.value = value;
			}

			public BlindType getTypeBlind() {
				return typeBlind;
			}

			public void setTypeBlind(BlindType typeBlind) {
				this.typeBlind = typeBlind;
			}

			public double getValue() {
				return value;
			}
			public void setValue(double value) {
				this.value = value;
			}


}
