package pokerServer.messageConverter.Impl;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pokerServer.messageConverter.MessageConverter;
import pokerServer.messages.IntroduceMessage;
import pokerServer.messages.Message;
@Service
public class MessageConverterImpl implements MessageConverter {

	@Override
	public Message convert(TextMessage te, WebSocketSession s) {
			String[] data=te.getPayload().split(":");
			String marker=data[0];
		if(marker.equals("introduce"))
				{
			
			return new IntroduceMessage(data[1],s);
		}
return null;
		

	}

}
