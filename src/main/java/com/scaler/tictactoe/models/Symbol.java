package com.scaler.tictactoe.models;

public class Symbol {
    private char character;

    public static final Symbol NULL_SYMBOL = new Symbol(' ');

    public Symbol(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}
