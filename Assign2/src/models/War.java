package models;

import java.util.ArrayList;

public abstract class War {

	CardDeck deck;
	ArrayList<Player> players;
	boolean gameOver;
	Player roundWinner, gameWinner;
	Logger logger;

	War() {
		this.players = new ArrayList<>();
		this.logger = new Logger();
		gameOver = false;
		setupDeck();
	}

	void addPlayer(Player player) {
		players.add(player);
	}

	void setupDeck() {
		this.deck = new CardDeck();
		deck.shuffle();
	}

	void dealDeck() {

		for (int i = 0; i < 52 % players.size(); i++) {
			deck.dealCard();
		}

		while (deck.hasCards()) {
			for (Player player : players) {
				player.addCardToHand(deck.dealCard());
			}
		}
	}

	public void play() {

		while (!gameOver && playersHaveCards()) {
			playRound();
		}

		logger.logGameWinner(this);
	}

	void playRound() {

		playCards();

		if (playersAtWar()) {
			war();
		}

		setRoundWinner();

		logger.logRoundWinner(roundWinner);

		cleanUpRound();
	}

	void playCards() {
		for (Player player : players) {
			if (player.hasCards()) {
				player.playCard();
			}
		}

		logger.logCardsPlayed(players);
	}

	private void war() {

		// ToDo: Fix, fails if players run out of cards during war, need to
		// check/fail

		while (playersAtWar() && playersHaveCards()) {
			logger.logWar();
			addCardsToDownPile();
			playCards();
		}

	}

	private boolean playersAtWar() {
		for (int i = 0; i < players.size(); i++) {
			for (int j = i + 1; j < players.size(); j++) {
				if (players.get(i).currCardOrdinalRank() == players.get(j).currCardOrdinalRank()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean playersHaveCards() {
		int playersWithCards = 0;
		for (Player player : players) {
			if (player.hasCards()) {
				playersWithCards++;
			}
		}

		return playersWithCards > 1;
	}

	private void addCardsToDownPile() {
		for (Player player : players) {
			if (player.hasCards()) {
				player.addCardToDownPile();
			}
		}
	}

	void setRoundWinner() {
		roundWinner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).currCardOrdinalRank() > roundWinner.currCardOrdinalRank()) {
				roundWinner = players.get(i);
			}
		}
	}

	abstract void cleanUpRound();

	abstract boolean hasWinner();

	abstract Player getGameWinner();
}
