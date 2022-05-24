package com.scaler.tictactoe.controllers;

import com.scaler.tictactoe.exceptions.EmptyMovesUndoOperationException;
import com.scaler.tictactoe.models.Board;
import com.scaler.tictactoe.models.Game;
import com.scaler.tictactoe.models.GameStatus;
import com.scaler.tictactoe.models.Move;
import com.scaler.tictactoe.models.Player;
import com.scaler.tictactoe.strategies.gamewinningstrategies.GameWinningStrategy;

import java.util.List;
import java.util.Scanner;

// Start a game
// Make a move
// Undo
// CheckWinner
// GetCurrentState
public class GameController {
    public Game createGame(int dimensionOfBoard,
                           List<Player> players,
                           List<GameWinningStrategy> strategies) {
        Game game = null;

        try {
            game = Game.create()
                    .withBoard(new Board(dimensionOfBoard))
                    .addPlayers(players)
                    .addGameWinningStrategies(strategies)
                    .build();
        } catch (Exception exception) {
            System.out.println("We did something wrong");
        }

        return game;
    }

    public MoveResult makeMove(Game game, Move move) {
        return game.move(move);
    }

    public boolean undo(Game game) {
        try {
            return game.undo();
        } catch (EmptyMovesUndoOperationException e) {
            //error message
            return false;
        }
    }

    public Player getWinner(Game game) {
        return null;
    }

    public GameStatus getGameStatus(Game game) {
        return GameStatus.IN_PROGRESS;
    }

    public GameStatus startGame(Game game) {
        while (game.getGameStatus()!=GameStatus.IN_PROGRESS) {
            if (input.equals("u")) {
                try {
                    game.undo();
                } catch (EmptyMovesUndoOperationException e) {
                    System.out.println("No moves to undo");
                }
            }
            else{
                game.playGame()
            }
        }
    }
}
