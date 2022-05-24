package com.scaler.tictactoe.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private final int dimension;
    private List<List<Cell>> board;

    public Board(int dimension) {
        this.dimension = dimension;
        board = new ArrayList<>();
        for (int i = 0; i < dimension; ++i) {
            board.add(new ArrayList<>());
            for (int j = 0; j < dimension; ++j) {
                board.get(i).add(new Cell(i, j));
            }
        }
    }

    public Cell getCell(int i, int j) {
        return board.get(i).get(j);
    }

    public boolean setSymbol(Symbol symbol, int i, int j) {
        if (getCell(i, j).getSymbol() == Symbol.NULL_SYMBOL) {
            board.get(i).get(j).setSymbol(symbol);
            return true;
        }
        return false;
    }

    public boolean isPlayable(int i, int j) {
        return getCell(i, j).getSymbol() == Symbol.NULL_SYMBOL;
    }


    public int getDimension() {
        return dimension;
    }

    @Override
    public String toString() {
        return board.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
