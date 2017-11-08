package models;

import java.util.ArrayList;

class CardPile {

	private ArrayList<Card> cards;

	CardPile() {
		this.cards = new ArrayList<>();
	}

	void addCard(Card card) {
		cards.add(card);
	}

	void addCards(ArrayList<Card> cards) {
		this.cards.addAll(cards);
	}

	void addCards(CardPile cards) {
		this.cards.addAll(cards.getCards());
	}

	int cardCount() {
		return cards.size();
	}

	ArrayList<Card> getCards() {
		ArrayList<Card> allCards = new ArrayList<>(cards);
		cards.clear();
		return allCards;
	}

}
