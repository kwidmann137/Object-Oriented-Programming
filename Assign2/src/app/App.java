package app;

import models.*;

public class App {

	public static void main(String[] args) {

		Player player1 = new Player("Bob");
		Player player2 = new Player("John");
		Player player3 = new Player("Izzy");

		try {

			War game = new WarVariation3(player1, player2, player3);

			game.play();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
