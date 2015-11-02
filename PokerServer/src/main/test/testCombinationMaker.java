
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pokerServer.Deck.Hand;
import pokerServer.Deck.Card.Card;
import pokerServer.Deck.Card.Rank;
import pokerServer.Deck.Card.Suit;
import pokerServer.Deck.Combination.CombinationMaker;
import pokerServer.Deck.Combination.Impl.CombinationMakerImpl;


public class testCombinationMaker {

	@Test
	public void test() {
		//given
		CombinationMaker combMaker=new CombinationMakerImpl();
		
		Card[] hand=new Card[2];
		
		Card[] table=new Card[5];
		
		hand[0]=new Card(Rank.ACE, Suit.CLUBS);
		hand[1]=new Card(Rank.EIGHT, Suit.DIAMONDS);

		table[0]=new Card(Rank.FIVE, Suit.DIAMONDS);
		table[1]=new Card(Rank.FIVE, Suit.HEARTS);
		table[2]=new Card(Rank.FIVE, Suit.SPADES);
		table[3]=new Card(Rank.SEVEN, Suit.DIAMONDS);
		table[4]=new Card(Rank.THREE, Suit.DIAMONDS);
		
		//when
		List<Hand> poss=combMaker.generateCombinations(hand, table);
		//then
		Assert.assertEquals(21, poss.size());
		
		
	}
	
	@Test
	public void testGetHand() {
	//given
	CombinationMaker combMaker=new CombinationMakerImpl();
	
	Card[] hand=new Card[2];
	
	Card[] table=new Card[5];
	
	hand[0]=new Card(Rank.ACE, Suit.CLUBS);
	hand[1]=new Card(Rank.FIVE, Suit.CLUBS);

	table[0]=new Card(Rank.FIVE, Suit.DIAMONDS);
	table[1]=new Card(Rank.FIVE, Suit.HEARTS);
	table[2]=new Card(Rank.FIVE, Suit.SPADES);
	table[3]=new Card(Rank.SEVEN, Suit.DIAMONDS);
	table[4]=new Card(Rank.THREE, Suit.DIAMONDS);
	
	//when
	Hand best=combMaker.getBestHand(hand, table);
	//then
	Assert.assertEquals(2, best.getDeck().size());
	
}
}
