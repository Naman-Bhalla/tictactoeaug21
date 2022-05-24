package com.scaler.tictactoe.strategies.botplayingstrategies;

import com.scaler.tictactoe.models.Board;
import com.scaler.tictactoe.models.Bot;
import com.scaler.tictactoe.models.Game;
import com.scaler.tictactoe.models.Move;
import com.scaler.tictactoe.models.Symbol;

import java.util.Random;

public class RandomBotPlayingStrategy implements BotPlayingStrategy {

    @Override
    public Move makeNextMove(Game game, Bot player) {
        int movesRemaining = game.getRemainingTurns();
        int rand = new Random().nextInt(movesRemaining);
        Board board = game.getBoard();
        for (int i = 0; i < board.getDimension(); ++i) {
            for (int j = 0; j < board.getDimension(); ++j) {
                if (board.isPlayable(i, j)) {
                    if (rand == 0) {
                        return new Move(player, i, j);
                    }
                    rand--;
                }
            }
        }

        return null;
    }
}
