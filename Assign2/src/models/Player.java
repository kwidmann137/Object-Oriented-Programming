package models;

import java.util.ArrayList;

public class Player {

	private String name;
	private Hand hand;
	private CardPile upPile;
	private CardPile downPile;
	private CardPile pointsPile;
	private Card upCard;

	public Player(String name) {

		this.name = name;
		this.hand = new Hand();
		this.upPile = new CardPile();
		this.downPile = new CardPile();
		this.pointsPile = new CardPile();
	}

	boolean hasCards() {
		return hand.hasCards();
	}

	Card drawCard() {
		if (hand.hasCards()) {
			return hand.drawCard();
		}
		return null;
	}

	void playCard() {
		upCard = hand.drawCard();
		upPile.addCard(upCard);
	}

	Card getUpCard() {
		return this.upCard;
	}

	void addCardToHand(Card card) {
		hand.addCard(card);
	}

	int currCardOrdinalRank() {
		return upCard.getOrdinalRank();
	}

	void addCardsToHand(CardPile cards) {
		hand.addCards(cards);
	}

	int getHandCount() {
		return hand.getCount();
	}

	void addCardToDownPile() {
		downPile.addCard(drawCard());
	}

	CardPile getDownPile() {
		return downPile;
	}

	CardPile getUpPile() {
		return upPile;
	}

	void addCardsToPointsPile(CardPile cards) {
		pointsPile.addCards(cards);
	}

	int getPointsPileCount() {
		return pointsPile.cardCount();
	}

	String getName() {
		return this.name;
	}
}
