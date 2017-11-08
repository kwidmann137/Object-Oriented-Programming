package models;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
	private ArrayList<Card> cards;

	CardDeck() {
		this.cards = new ArrayList<>();

		for (CardSuit suit : CardSuit.values()) {
			for (CardRank rank : CardRank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
	}

	void shuffle() {

		Collections.shuffle(cards);

	}

	Card dealCard() {
		if (cards.size() > 0) {
			Card topCard = cards.get(0);
			cards.remove(0);
			return topCard;
		}
		return null;
	}

	boolean hasCards() {
		return this.cards.size() > 0;
	}

	public void addCardToDeck(Card card) {
		cards.add(card);
	}
}
