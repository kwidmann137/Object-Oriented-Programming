package models;

import java.util.ArrayList;

class Hand {

	private ArrayList<Card> cards;

	Hand() {
		this.cards = new ArrayList<Card>();
	}

	Card drawCard() {
		if (cards.size() > 0) {
			Card topCard = cards.get(0);
			cards.remove(0);
			return topCard;
		}
		// ToDo: Refactor so not returning null
		return null;
	}

	void addCard(Card card) {
		this.cards.add(card);
	}

	void addCards(ArrayList<Card> cards) {
		if (cards != null) {
			this.cards.addAll(cards);
		}
	}

	void addCards(CardPile cards) {
		if (cards != null) {
			this.cards.addAll(cards.getCards());
		}
	}

	boolean hasCards() {
		return cards.size() > 0;
	}

	int getCount() {
		return cards.size();
	}

}
