package models;

import java.util.ArrayList;

class Logger {

	void logCardsPlayed(ArrayList<Player> players) {
		for (Player player : players) {
			System.out.printf("%s plays %s of %s\n", player.getName(), player.getUpCard().getRank(),
					player.getUpCard().getSuit());
		}
	}

	void logRoundWinner(Player roundWinner) {
		System.out.printf("%s won the round\n", roundWinner.getName());
	}

	void logWar() {
		System.out.println("WAR!");
	}

	void logScoreByHand(ArrayList<Player> players) {
		System.out.printf("Score is ");
		for (Player player : players) {
			System.out.printf("%s %s, ", player.getName(), player.getHandCount());
		}
		System.out.println();

	}

	void logScoreByPointsPile(ArrayList<Player> players) {
		System.out.printf("Score is ");
		for (Player player : players) {
			System.out.printf("%s %s, ", player.getName(), player.getPointsPileCount());
		}
		System.out.println();
	}

	void logGameWinner(War game) {
		if (game.hasWinner()) {
			Player winner = game.getGameWinner();
			System.out.printf("%s wins!\n", winner.getName());
		} else {
			System.out.println("Tie game!\n");
		}
	}

}
