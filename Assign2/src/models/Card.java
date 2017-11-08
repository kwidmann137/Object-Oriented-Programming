package models;

class Card {

	private CardSuit suit;
	private CardRank rank;

	Card(CardSuit suit, CardRank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	int getOrdinalRank() {
		return rank.ordinal();
	}

	int getOrdinalSuit() {
		return suit.ordinal();
	}

	String getRank() {
		return rank.name();
	}

	String getSuit() {
		return suit.name();
	}
}
