package models;

public class WarVariation3 extends War {

	Player player1, player2, player3;

	public WarVariation3(Player player1, Player player2, Player player3) {
		super();
		addPlayer(player1);
		addPlayer(player2);
		addPlayer(player3);
		dealDeck();
	}

	boolean hasWinner() {
		return !(players.get(0).getPointsPileCount() == players.get(1).getPointsPileCount());
	}

	void cleanUpRound() {

		for (Player player : players) {
			roundWinner.addCardsToPointsPile(player.getUpPile());
			roundWinner.addCardsToPointsPile(player.getDownPile());
		}

		logger.logScoreByPointsPile(players);
	}

	Player getGameWinner() {

		gameWinner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getPointsPileCount() > gameWinner.getPointsPileCount()) {
				gameWinner = players.get(i);
			}
		}

		return gameWinner;
	}

}
