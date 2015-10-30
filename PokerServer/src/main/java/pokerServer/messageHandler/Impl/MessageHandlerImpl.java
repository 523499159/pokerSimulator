package pokerServer.messageHandler.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pokerServer.Client.Client;
import pokerServer.broadcaster.Broadcast;
import pokerServer.matchPlayer.MatchPlayer;
import pokerServer.messageHandler.MessageHandler;
import pokerServer.messages.DecisionMessage;
import pokerServer.messages.IntroduceMessage;
import pokerServer.messages.Message;
import pokerServer.messages.PossibleDecisions;
import pokerServer.messages.TypeMessage;
import pokerServer.sessionHandler.SessionHandler;

@Service
public class MessageHandlerImpl implements MessageHandler {
    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    Broadcast broadcaster;
    
    @Autowired
    MatchPlayer playedMatch;
    
	@Override
	public void handleMessage(Message message) {
	
		if (message.getType().equals(TypeMessage.INTRODUCE)) {
		IntroduceMessage intro = (IntroduceMessage) message;
		handleIntro(intro);
		}

		if (message.getType().equals(TypeMessage.DECISION)) {
			DecisionMessage msg = (DecisionMessage) message;
		handleDecision(msg);
		}
	}
		
		private void handleDecision(DecisionMessage msg) {
	

			if(msg.getDecision().equals(PossibleDecisions.PASS)){
				playedMatch.clientPass(msg.getSession());
			}
			
			if(msg.getDecision().equals(PossibleDecisions.CHECK)){

			Client c=playedMatch.getPlayerFromSession(msg.getSession());
			c.addToMoneyPutedInRound(msg.getValue());
			
			
			}
			
			if(msg.getDecision().equals(PossibleDecisions.RAISE)){
				Client c=playedMatch.getPlayerFromSession(msg.getSession());
				c.addToMoneyPutedInRound(msg.getValue());
			}
			
			
			if(msg.getDecision().equals(PossibleDecisions.WAIT)){

			}
			
			playedMatch.addDecision(msg);
			
		
	}

		private void handleIntro(IntroduceMessage intro){
			try {
				Client c=sessionHandler.getClientFromSession(intro.getSession());
				c.introduceAndReadyForPlay(intro.getName());
				broadcaster.broadcast("Witaj, "+c.getName(), c);
			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
				
	
		
	

	
	
	
	
}
