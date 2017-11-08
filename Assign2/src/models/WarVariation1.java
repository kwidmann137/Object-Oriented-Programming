package models;

public class WarVariation1 extends War {

	private int handsPlayed;

	public WarVariation1(Player player1, Player player2) {
		super();
		addPlayer(player1);
		addPlayer(player2);
		handsPlayed = 0;
		dealDeck();
	}

	boolean hasWinner() {
		return !(players.get(0).getHandCount() == players.get(1).getHandCount());
	}

	void cleanUpRound() {

		for (Player player : players) {
			roundWinner.addCardsToHand(player.getUpPile());
			roundWinner.addCardsToHand(player.getDownPile());
		}

		handsPlayed++;
		if (handsPlayed > 100) {
			gameOver = true;
		}

		logger.logScoreByHand(players);
	}

	Player getGameWinner() {

		gameWinner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getHandCount() > gameWinner.getHandCount()) {
				gameWinner = players.get(i);
			}
		}

		return gameWinner;
	}
}
