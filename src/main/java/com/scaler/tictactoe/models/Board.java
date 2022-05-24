package com.scaler.tictactoe.models;

import com.scaler.tictactoe.controllers.MoveResult;
import com.scaler.tictactoe.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> board;

    public Board(int dimension) {
        this.dimension = dimension;
        board = new ArrayList<>();
        for ( int i = 0; i < dimension; i++ ) {
            List<Cell> row = new ArrayList<>();
            for ( int j = 0; j < dimension; j++ ) {
                row.add(new Cell(i, j));
            }
            board.add(row);
        }
    }

    public Cell getCell(int i, int j) {
        return board.get(i).get(j);
    }

    public MoveResult makeMove(Move move) throws InvalidMoveException {
        Cell cell = board.get(move.getRow()).get(move.getColumn());
        if ( cell.isMarked() ) throw new InvalidMoveException();
        cell.setSymbol(move.getPlayer().getSymbol());
        return MoveResult.Success;
    }
}
