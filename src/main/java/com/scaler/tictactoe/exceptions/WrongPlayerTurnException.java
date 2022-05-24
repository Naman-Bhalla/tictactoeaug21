package com.scaler.tictactoe.exceptions;

public class WrongPlayerTurnException extends Exception {
  public WrongPlayerTurnException() {
    super("This is not the turn of the current player");
  }
}
