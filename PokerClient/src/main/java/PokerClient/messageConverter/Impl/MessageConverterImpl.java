package PokerClient.messageConverter.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import PokerClient.Card.Card;
import PokerClient.Card.Rank;
import PokerClient.Card.Suit;
import PokerClient.message.MoneyMessageType;
import PokerClient.message.MoneyMessage;
import PokerClient.message.CardMessage;
import PokerClient.message.CardPlace;
import PokerClient.message.ChangeStateMessage;
import PokerClient.message.Message;
import PokerClient.message.PlayerMessage;
import PokerClient.message.SimpleMessage;
import PokerClient.message.State;
import PokerClient.messageConverter.MessageConverter;

@Service
public class MessageConverterImpl implements MessageConverter {

	@Override
	public Message convert(TextMessage te) {
		String[] data = te.getPayload().split(":");
		String marker = data[0];

		if (!marker.contains("@")) {
			return new SimpleMessage(te.getPayload());
		}
		if (marker.contains("@")) {

			if(marker.equals("@MONEY")){

					String pivot=data[1];
				if (pivot.equals("@SMALLBLIND")) {
					return new MoneyMessage(MoneyMessageType.SMALL, Double.parseDouble(data[2]));
				}
				if (pivot.equals("@BIGBLIND")) {
					return new MoneyMessage(MoneyMessageType.BIG, Double.parseDouble(data[2]));
				}

				if (pivot.equals("@WALLET")) {
					return new MoneyMessage(MoneyMessageType.WALLET, Double.parseDouble(data[2]));
				}

				if (pivot.equals("@WALLET_START")) {
					return new MoneyMessage(MoneyMessageType.WALLET_START, Double.parseDouble(data[2]));
				}
				if (pivot.equals("@CURRENT_BET")) {
					return new MoneyMessage(MoneyMessageType.CURRENT_BET, Double.parseDouble(data[2]));
				}
			}

			if(marker.equals("@CHANGE_STATE")){

				String pivot=data[1];
				if (pivot.equals("@ENABLE")) {
					return new ChangeStateMessage(State.ENABLE);
				}
				if (pivot.equals("@DISABLE")) {
					return new ChangeStateMessage(State.DISABLE);
				}
			}

			if (marker.equals("@CARD")) {
				return new CardMessage(new Card(Rank.valueOf(data[1]), Suit.valueOf(data[2])), CardPlace.HAND);
			}

			if (marker.equals("@PLAYER")) {
				return new PlayerMessage(data[1]);
			}

			if (marker.equals("@TABLE")) {
					String tablePointer = data[1];
					if (tablePointer.equals("@CARD")) {

						return new CardMessage(new Card(Rank.valueOf(data[2]), Suit.valueOf(data[3])), CardPlace.TABLE);
					}

					if (tablePointer.equals("@MONEY")) {

						return new MoneyMessage(MoneyMessageType.TABLE, Double.parseDouble(data[2]));
					}

			}
		}

		return null;

	}

}
