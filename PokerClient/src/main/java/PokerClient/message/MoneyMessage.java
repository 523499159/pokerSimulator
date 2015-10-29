package PokerClient.message;

public class MoneyMessage extends Message {
			private MoneyMessageType typeMoney;
			double value;



			public MoneyMessage(MoneyMessageType type, double value) {
				super(MessageType.MONEY_VALUE);
				this.typeMoney = type;
				this.value = value;
			}

			public MoneyMessageType getTypeBlind() {
				return typeMoney;
			}

			public void setTypeBlind(MoneyMessageType typeBlind) {
				this.typeMoney = typeBlind;
			}

			public double getValue() {
				return value;
			}
			public void setValue(double value) {
				this.value = value;
			}


}
