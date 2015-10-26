package pokerServer.messageHandler.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import pokerServer.RandomTimeResponderSimulator;
import pokerServer.messageHandler.MessageHandler;
import pokerServer.messages.IntroduceMessage;
import pokerServer.messages.Message;

@Service
public class MessageHandlerImpl implements MessageHandler {
    @Autowired
    private RandomTimeResponderSimulator randomTicker;

	@Override
	public void handleMessage(Message message) {
	
		if (message instanceof IntroduceMessage) {
		IntroduceMessage intro = (IntroduceMessage) message;
		handleIntro(intro);
		}
	}
		
		private void handleIntro(IntroduceMessage intro){
			try {
				randomTicker.broadcast("Witamy w grze: "+intro.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
				
	
		
	

	
	
	
	
}
