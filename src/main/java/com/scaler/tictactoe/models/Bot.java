package com.scaler.tictactoe.models;

import com.scaler.tictactoe.strategies.botplayingstrategies.BotPlayingStrategy;

public class Bot extends Player {
  private BotPlayingStrategy botPlayingStrategy;

  public Bot(Symbol symbol, BotPlayingStrategy strategy) {
    super(PlayerType.BOT, symbol);
    this.botPlayingStrategy = strategy;
  }

  public Move getMove(Board board) {
    return botPlayingStrategy.makeNextMove(board, this.getSymbol());
  }
}
