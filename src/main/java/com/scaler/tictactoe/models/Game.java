package com.scaler.tictactoe.models;

import com.scaler.tictactoe.controllers.MoveResult;
import com.scaler.tictactoe.exceptions.EmptyMovesUndoOperationException;
import com.scaler.tictactoe.exceptions.InvalidMoveException;
import com.scaler.tictactoe.exceptions.MultipleBotsException;
import com.scaler.tictactoe.exceptions.WrongPlayerTurnException;
import com.scaler.tictactoe.strategies.gamewinningstrategies.GameWinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private List<Player> players;
  private Board board;
  private List<Move> moves;
  private List<GameWinningStrategy> gameWinningStrategies;
  private int lastMovedPlayerIndex;
  private GameStatus gameStatus;
  private Player winner;

  private Game(Builder builder) {
    this.gameStatus = builder.gameStatus;
    this.players = builder.players;
    this.board = builder.board;
    this.moves = builder.moves;
    this.gameWinningStrategies = builder.gameWinningStrategies;
    this.lastMovedPlayerIndex = builder.lastMovedPlayerIndex;
    this.winner = builder.winner;
  }

  public static Builder create() {
    return new Builder();
  }

  public MoveResult makeMove(Move move) throws WrongPlayerTurnException, InvalidMoveException {
    int nextPlayerIndex = (lastMovedPlayerIndex + 1) % players.size();
    int playerIndex = players.indexOf(move.getPlayer());
    if (playerIndex != nextPlayerIndex) {
      throw new WrongPlayerTurnException();
    }
    MoveResult moveResult = board.makeMove(move);
    for (GameWinningStrategy gameWinningStrategy : gameWinningStrategies) {
      if (gameWinningStrategy.checkIfWon(board, move.getCell())) {
        this.winner = move.getPlayer();
        this.gameStatus = GameStatus.ENDED;
        return MoveResult.GameEnd;
      }
    }
    lastMovedPlayerIndex = nextPlayerIndex;
    nextPlayerIndex = (lastMovedPlayerIndex + 1) % players.size();
    if (players.get(nextPlayerIndex).getPlayerType().equals(PlayerType.BOT)) {
      Bot bot = (Bot) players.get(nextPlayerIndex);
      Move nextMove = bot.getMove(this.board);
      this.makeMove(move);
    }
    return moveResult;
  }

  // A B
  // | |
  // |
  // 0 1 (2 % 2 = 0) 1 (2 % 2 = 0)
  // 0 -> -1
  public boolean undo() throws EmptyMovesUndoOperationException {
    if (this.moves.size() == 0) {
      // Handle Edge Case
      throw new EmptyMovesUndoOperationException();
    }
    Move lastMove = this.moves.get(this.moves.size() - 1);
    Cell relevantCell = lastMove.getCell();
    relevantCell.clearCell();
    this.lastMovedPlayerIndex -= 1;
    this.lastMovedPlayerIndex =
        (this.lastMovedPlayerIndex + this.players.size()) % this.players.size();
    this.moves.remove(relevantCell);
    return true;
  }

  public static class Builder {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private List<GameWinningStrategy> gameWinningStrategies;
    private int lastMovedPlayerIndex;
    private GameStatus gameStatus;
    private Player winner;

    Builder() {
      this.players = new ArrayList<>();
      this.gameWinningStrategies = new ArrayList<>();
      this.moves = new ArrayList<>();
      this.lastMovedPlayerIndex = -1;
      this.gameStatus = GameStatus.IN_PROGRESS;
    }

    public Builder addPlayer(Player player) {
      this.players.add(player);
      return this;
    }

    public Builder addPlayers(List<Player> players) {
      this.players.addAll(players);
      return this;
    }

    public Builder withBoard(Board board) {
      this.board = board;
      return this;
    }

    //        public Builder setGameWinningStrategies(List<GameWinningStrategy>
    // gameWinningStrategies) {
    //            this.gameWinningStrategies = gameWinningStrategies;
    //        }

    public Builder addGameWinningStrategy(GameWinningStrategy strategy) {
      this.gameWinningStrategies.add(strategy);
      return this;
    }

    public Builder addGameWinningStrategies(List<GameWinningStrategy> strategies) {
      this.gameWinningStrategies.addAll(strategies);
      return this;
    }

    private boolean checkIfSingleBotMax() {
      int count = 0;

      for (Player player : players) {
        if (player.getPlayerType().equals(PlayerType.BOT)) {
          count += 1;
        }
      }

      return count <= 1;
    }

    public Game build() throws MultipleBotsException {
      if (!checkIfSingleBotMax()) {
        throw new MultipleBotsException();
      }

      return new Game(this);
    }
  }
}
