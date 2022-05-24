package com.scaler.tictactoe.strategies.gamewinningstrategies;

import com.scaler.tictactoe.models.Board;
import com.scaler.tictactoe.models.Cell;
import com.scaler.tictactoe.models.Symbol;

import java.util.HashMap;

public class OrderOneGameWinningStrategy implements GameWinningStrategy {
  HashMap<Integer, HashMap<Character, Integer>> rowMap;
  HashMap<Integer, HashMap<Character, Integer>> columnMap;

  Integer boardDimension;

  public OrderOneGameWinningStrategy(int boardDimension) {
    this.boardDimension = boardDimension;
    rowMap = new HashMap<>();
    columnMap = new HashMap<>();
    for (int i = 0; i < boardDimension; i++) {
      rowMap.put(i, new HashMap<>());
      columnMap.put(i, new HashMap<>());
    }
  }

  @Override
  public boolean checkIfWon(Board board, Cell lastMoveCell) {
    Symbol symbol = board.getCell(lastMoveCell.getRow(), lastMoveCell.getColumn()).getSymbol();
    HashMap<Character, Integer> currentRow = rowMap.get(lastMoveCell.getRow());
    currentRow.compute(symbol.getCharacter(), (key, val) -> (val == null) ? 1 : val + 1);
    HashMap<Character, Integer> currColumn = columnMap.get(lastMoveCell.getColumn());
    currColumn.compute(symbol.getCharacter(), (key, val) -> (val == null) ? 1 : val + 1);
    return columnMap.get(symbol.getCharacter()).equals(boardDimension)
        || rowMap.get(symbol.getCharacter()).equals(boardDimension);
  }
}
