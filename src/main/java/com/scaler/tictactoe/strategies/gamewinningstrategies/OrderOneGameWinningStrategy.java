package com.scaler.tictactoe.strategies.gamewinningstrategies;

import com.scaler.tictactoe.models.Board;
import com.scaler.tictactoe.models.Cell;
import com.scaler.tictactoe.models.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderOneGameWinningStrategy implements GameWinningStrategy {

    Board board;
    int moveCount;

    HashMap<Symbol, SymbolCount> symbolCounts;

    public OrderOneGameWinningStrategy(Board board) {
        this.board = board;
        moveCount = 0;
        symbolCounts = new HashMap<>();
    }

    @Override
    public boolean checkIfWon(Cell lastMoveCell) {
        Symbol symbol = lastMoveCell.getSymbol();
        SymbolCount symbolCount = symbolCounts.computeIfAbsent(symbol, k -> new SymbolCount(board.getDimension()));
        moveCount++;
        return symbolCount.applyMove(lastMoveCell.getRow(), lastMoveCell.getColumn());
    }


    public static class SymbolCount {

        int dimension;
        ArrayList<Integer> horizontalCount;
        ArrayList<Integer> verticalCount;
        Integer upDiagonalCount;
        Integer downDiagonalCount;

        SymbolCount(int dimension) {
            this.dimension = dimension;
            horizontalCount = new ArrayList<>();
            verticalCount = new ArrayList<>();
            upDiagonalCount = 0;
            downDiagonalCount = 0;

            for (int i = 0; i < dimension; ++i) {
                horizontalCount.add(0);
                verticalCount.add(0);
            }
        }

        //true for success, false otherwise
        boolean applyMove(int row, int column) {
            int hNew = horizontalCount.get(row) + 1;
            int vNew = verticalCount.get(column) + 1;
            horizontalCount.set(row, hNew);
            verticalCount.set(column, vNew);
            if (row == column) {
                upDiagonalCount++;
            }
            if (row + column == dimension - 1) {
                downDiagonalCount++;
            }
            return hNew == dimension || vNew == dimension || upDiagonalCount == dimension || downDiagonalCount == dimension;
        }

    }
}
