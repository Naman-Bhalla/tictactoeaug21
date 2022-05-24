package com.scaler.tictactoe.models;

public class Move {
  private Symbol symbol;
  private Cell cell;
  private Player player;

  public Move(int row, int column, Player player) {
    this.symbol = player.getSymbol();
    this.cell = new Cell(row, column);
    this.player = player;
  }

  public Cell getCell() {
    return cell;
  }

  public Player getPlayer() {
    return player;
  }
}

// IBotPlayingStrategy
// BotPlayingStrategyImpl
