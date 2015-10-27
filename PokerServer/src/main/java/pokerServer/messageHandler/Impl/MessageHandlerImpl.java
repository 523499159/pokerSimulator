package pokerServer.messageHandler.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pokerServer.Client.Client;
import pokerServer.broadcaster.Broadcast;
import pokerServer.messageHandler.MessageHandler;
import pokerServer.messages.IntroduceMessage;
import pokerServer.messages.Message;
import pokerServer.sessionHandler.SessionHandler;

@Service
public class MessageHandlerImpl implements MessageHandler {
    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    Broadcast broadcaster;
    
	@Override
	public void handleMessage(Message message) {
	
		if (message instanceof IntroduceMessage) {
		IntroduceMessage intro = (IntroduceMessage) message;
		handleIntro(intro);
		}
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
