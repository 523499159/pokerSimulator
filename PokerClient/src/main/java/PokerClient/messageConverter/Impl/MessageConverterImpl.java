package PokerClient.messageConverter.Impl;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import PokerClient.Card.Card;
import PokerClient.Card.Rank;
import PokerClient.Card.Suit;
import PokerClient.message.BlindType;
import PokerClient.message.BlindValueMessage;
import PokerClient.message.CardMessage;
import PokerClient.message.CardPlace;
import PokerClient.message.Message;
import PokerClient.message.PlayerMessage;
import PokerClient.message.SimpleMessage;
import PokerClient.messageConverter.MessageConverter;


@Service
public class MessageConverterImpl implements MessageConverter {

	@Override
	public Message convert(TextMessage te) {
			String[] data=te.getPayload().split(":");
			String marker=data[0];

		if(!marker.contains("@")){
			return new SimpleMessage(te.getPayload());
		}
		if(marker.contains("@")){
			if(marker.equals("@SMALLBLIND")){
				return new BlindValueMessage(BlindType.SMALL, Double.parseDouble(data[1]));
			}
			if(marker.equals("@BIGBLIND")){
				return new BlindValueMessage(BlindType.BIG, Double.parseDouble(data[1]));
			}
			if(marker.equals("@CARD")){
				return new CardMessage(
						new Card(Rank.valueOf(data[1]),Suit.valueOf(data[2])),CardPlace.HAND
						);
			}

			if(marker.equals("@PLAYER")){
				return new PlayerMessage(data[1]);
			}

			if(marker.equals("@TABLE")){
				String tablePointer=data[1];
				if(tablePointer.equals("@CARD")){

				return new CardMessage(
						new Card(Rank.valueOf(data[2]),Suit.valueOf(data[3])),CardPlace.TABLE
						);
			}

		}
		}


return null;


	}

}
