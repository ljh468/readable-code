package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.BoardIndexConverter;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

  public static final Scanner SCANNER = new Scanner(System.in);

  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

  @Override
  public CellPosition getCellPositionFromUser() {
    String userInput = SCANNER.nextLine();
    int colIndex = boardIndexConverter.getSelectedColIndex(userInput);
    int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);
    return CellPosition.of(rowIndex, colIndex);
  }

  @Override
  public String getUserInput() {
    return SCANNER.nextLine();
  }

}
