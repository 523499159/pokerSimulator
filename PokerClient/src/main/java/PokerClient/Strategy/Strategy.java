package PokerClient.Strategy;

import PokerClient.Card.Card;

public interface Strategy {

String getMsgToSend(Card[] hand, Card[] table, Double moneyOnTable,Double wallet, Double moneyToCheck);

}
