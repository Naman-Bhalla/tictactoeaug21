package com.scaler.tictactoe.strategies.botplayingstrategies;

import com.scaler.tictactoe.models.Board;
import com.scaler.tictactoe.models.Bot;
import com.scaler.tictactoe.models.Game;
import com.scaler.tictactoe.models.Move;
import com.scaler.tictactoe.models.Player;
import com.scaler.tictactoe.models.Symbol;

public interface BotPlayingStrategy {
    Move makeNextMove(Game game, Bot player);
}
