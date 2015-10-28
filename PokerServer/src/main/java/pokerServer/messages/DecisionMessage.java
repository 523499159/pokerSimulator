package pokerServer.messages;

import org.springframework.web.socket.WebSocketSession;

public class DecisionMessage extends Message{

		private PossibleDecisions decision;
		private Double value;
		
	
		
		public DecisionMessage( WebSocketSession s, PossibleDecisions decision, Double value) {
			super(TypeMessage.DECISION, s);
			this.decision = decision;
			this.value = value;
		}
		public PossibleDecisions getDecision() {
			return decision;
		}
		public void setDecision(PossibleDecisions decision) {
			this.decision = decision;
		}
		public Double getValue() {
			return value;
		}
		public void setValue(Double value) {
			this.value = value;
		}
		
		

}
