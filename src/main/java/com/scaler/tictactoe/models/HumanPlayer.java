package com.scaler.tictactoe.models;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private User user;

    public HumanPlayer(Symbol symbol, User user) {
        super(PlayerType.HUMAN, symbol);
        this.user = user;
    }

    @Override
    public Move makeMove(Game game) {
        Scanner sc = new Scanner(System.in);
        return new Move(this, sc.nextInt(), sc.nextInt());
    }
}

// List<Player> players;
// for (Player p: players) {
//   if (p.getType() == HUMAN) {
//   } else if (p.getType() == )
// }
