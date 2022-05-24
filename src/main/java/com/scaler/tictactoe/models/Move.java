package com.scaler.tictactoe.models;

public class Move {
    private Player player;
    int row;
    int column;

    public Move(Player player, int row, int column) {
        this.player = player;
        this.row = row;
        this.column =column;
    }

    public Player getPlayer() {
        return player;
    }
}

// IBotPlayingStrategy
// BotPlayingStrategyImpl