package pokerServer.messageConverter.Impl;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import pokerServer.messageConverter.MessageConverter;
import pokerServer.messages.DecisionMessage;
import pokerServer.messages.IntroduceMessage;
import pokerServer.messages.Message;
import pokerServer.messages.PossibleDecisions;
import pokerServer.resultAnalyzer.PossibleValues;
@Service
public class MessageConverterImpl implements MessageConverter {

	@Override
	public Message convert(TextMessage te, WebSocketSession s) {
			String[] data=te.getPayload().split(":");
			String marker=data[0];
		if(marker.equals("@INTRODUCE"))
				{
			
			return new IntroduceMessage(data[1],s);
		}
		
		if(marker.equals("@DECISION"))
		{
			DecisionMessage msg;
		PossibleDecisions decision=PossibleDecisions.valueOf(data[1].replace("@",""));
	
			
			
			if(decision.equals(PossibleDecisions.PASS)){
	
				msg= new DecisionMessage(s, PossibleDecisions.PASS,0.0 );

				return msg;
			}

			if(decision.equals(PossibleDecisions.CHECK)){
				msg= new DecisionMessage(s, PossibleDecisions.CHECK,Double.parseDouble(data[2]) );

				return msg;
			}
			
			if(decision.equals(PossibleDecisions.RAISE)){
				msg= new DecisionMessage(s, PossibleDecisions.RAISE,Double.parseDouble(data[2]) );
			
				return msg;
			}
		}
		
		
		
		
		return null;
		

	}

}
