package com.scaler.tictactoe.exceptions;

public class InvalidMoveException extends Exception {
  public InvalidMoveException() {
    super("Invalid move");
  }
}
