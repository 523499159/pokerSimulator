package PokerClient.messageConverter.Impl;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;


import PokerClient.message.Message;
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


return null;


	}

}
