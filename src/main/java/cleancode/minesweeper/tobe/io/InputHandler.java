package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.position.CellPosition;

public interface InputHandler {

  CellPosition getCellPositionFromUser();

  String getUserInput();
}
