package com.scaler.tictactoe.models;

import com.scaler.tictactoe.controllers.MoveResult;
import com.scaler.tictactoe.exceptions.DuplicateSymbolException;
import com.scaler.tictactoe.exceptions.EmptyMovesUndoOperationException;
import com.scaler.tictactoe.exceptions.MultipleBotsException;
import com.scaler.tictactoe.strategies.gamewinningstrategies.GameWinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private List<GameWinningStrategy> gameWinningStrategies;
    private int lastMovedPlayerIndex;

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private GameStatus gameStatus;
    private Player winner;
    int remainingTurns;

    public Game(List<Player> players, Board board, List<GameWinningStrategy> gameWinningStrategies) {
        this.players = players;
        this.board = board;
        this.gameWinningStrategies = gameWinningStrategies;
        moves = new ArrayList<>();
        lastMovedPlayerIndex = -1;
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public static Builder create() {
        return new Builder();
    }

    public boolean undo() throws EmptyMovesUndoOperationException {
        if (this.moves.size() == 0) {
            // Handle Edge Case
            throw new EmptyMovesUndoOperationException();
        }
        Move lastMove = this.moves.get(this.moves.size() - 1);
        Cell relevantCell = board.getCell(lastMove.row, lastMove.column);
        relevantCell.clearCell();
        this.lastMovedPlayerIndex -= 1;
        this.lastMovedPlayerIndex = (this.lastMovedPlayerIndex + this.players.size()) % this.players.size();
        this.moves.remove(relevantCell);
        return true;
    }

    public MoveResult move(Move move) {
        Cell cell = board.getCell(move.row, move.column);
        if (board.setSymbol(move.getPlayer().getSymbol(), cell.getRow(), cell.getColumn())) {
            boolean win = false;
            moves.add(move);
            for (GameWinningStrategy gameWinningStrategy : gameWinningStrategies) {
                win = win || gameWinningStrategy.checkIfWon(cell);
            }
            if (win) {
                gameStatus = GameStatus.ENDED;
            } else if (getRemainingTurns() == 0) {
                gameStatus = GameStatus.DRAW;
            }
            return MoveResult.SUCCESS;
        }
        System.out.println(board);
        return MoveResult.FAILURE;
    }

    public GameStatus playGame() {
        while (moves.size() < board.getDimension() * board.getDimension() && gameStatus == GameStatus.IN_PROGRESS) {
            int nextPlayer = (lastMovedPlayerIndex + players.size() - 1) % players.size();
            Player player = players.get(nextPlayer);
            Move playerMove = player.makeMove(this);
            while (move(playerMove) != MoveResult.SUCCESS) {
                System.out.println("Invalid move");
                playerMove = player.makeMove(this);
            }
            lastMovedPlayerIndex = nextPlayer;
            System.out.println("Move: " + playerMove.row + " " + playerMove.column);
            System.out.println("Board:\n" + board.toString());
            if (gameStatus == GameStatus.DRAW) {
                System.out.println("Game is draw");
            }
        }
        if (gameStatus == GameStatus.ENDED) {
            winner = players.get(lastMovedPlayerIndex);
            System.out.println("winner: " + winner.getSymbol());
        }
        return gameStatus;
    }


    public int getRemainingTurns() {
        return board.getDimension() * board.getDimension() - moves.size();
    }

    public Board getBoard() {
        return board;
    }

    public static class Builder {
        private List<Player> players;
        private Board board;
        private List<GameWinningStrategy> gameWinningStrategies;

        Builder() {
            this.players = new ArrayList<>();
            this.gameWinningStrategies = new ArrayList<>();
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
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

        public Builder setGameWinningStrategies(List<GameWinningStrategy> gameWinningStrategies) {
            this.gameWinningStrategies = gameWinningStrategies;
            return this;
        }

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

        private boolean checkIfSymbolsUnique() {
            HashSet<Character> symbols = new HashSet<>();
            for (Player player : players) {
                if (!symbols.add(player.getSymbol().getCharacter())) {
                    return false;
                }
            }
            return true;
        }

        public Game build() throws MultipleBotsException, DuplicateSymbolException {
            if (!checkIfSingleBotMax()) {
                throw new MultipleBotsException();
            }

            if (!checkIfSymbolsUnique()) {
                throw new DuplicateSymbolException();
            }

            return new Game(players, board, gameWinningStrategies);
        }
    }
}
