package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

  public static final int BOARD_ROW_SIZE = 8;
  public static final int BOARD_COL_SIZE = 10;
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COL_SIZE);
  private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
  private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

  public void run() {
    consoleOutputHandler.showGameStartComments();
    gameBoard.initializeGame();

    while (true) {
      try {
        consoleOutputHandler.showBoard(gameBoard);

        if (doesUserWinTheGame()) {
          consoleOutputHandler.printGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          consoleOutputHandler.printGameLosingComment();
          break;
        }

        String callInput = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(callInput, userActionInput);
      } catch (GameException e) {
        consoleOutputHandler.printExceptionMessage(e);
      } catch (Exception e) {
        // 예상치 못한 예외를 잡음
        consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
        // e.printStackTrace(); // 안티패턴 -> 실제는 로그를 남김
      }
    }
  }

  private void actOnCell(String callInput, String userActionInput) {
    int selectedColIndex = getSelectedColIndex(callInput);
    int selectedRowIndex = getSelectedRowIndex(callInput);

    // early return
    if (doesUserChooseToPlantFlag(userActionInput)) {
      gameBoard.flag(selectedRowIndex, selectedColIndex);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userActionInput)) {
      if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
        gameBoard.open(selectedRowIndex, selectedColIndex);
        changeGameStatusToLose();
        return;
      }

      gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
      checkIfGameIsOver();
      return;
    }

    // System.out.println("잘못된 번호를 선택하셨습니다.");
    throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
  }

  private void changeGameStatusToLose() {
    gameStatus = -1;
  }

  private void changeGameStatusToWin() {
    gameStatus = 1;
  }

  private boolean doesUserChooseToOpenCell(String userActionInput) {
    return userActionInput.equals("1");
  }

  private boolean doesUserChooseToPlantFlag(String userActionInput) {
    return userActionInput.equals("2");
  }

  private int getSelectedRowIndex(String callInput) {
    char cellInputRow = callInput.charAt(1);
    return convertRowFrom(cellInputRow);
  }

  private int getSelectedColIndex(String callInput) {
    char cellInputCol = callInput.charAt(0);
    return convertColFrom(cellInputCol); // cellInput으로 부터 변환
  }

  private String getCellInputFromUser() {
    consoleOutputHandler.printCommentForUserAction();
    return consoleInputHandler.getUserInput();
  }

  private String getUserActionInputFromUser() {
    consoleOutputHandler.printCommentForSelectingCell();
    return consoleInputHandler.getUserInput();
  }

  private boolean doesUserLoseTheGame() {
    return gameStatus == -1;
  }

  private boolean doesUserWinTheGame() {
    return gameStatus == 1;
  }

  private void checkIfGameIsOver() {
    if (gameBoard.isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

  private int convertRowFrom(char cellInputRow) {
    int rowIndex = Character.getNumericValue(cellInputRow) - 1;
    if (rowIndex >= BOARD_ROW_SIZE) {
      throw new GameException("잘못된 입력입니다.");
    }
    return rowIndex;
  }

  private int convertColFrom(char cellInputCol) {
    return switch (cellInputCol) {
      case 'a' -> 0;
      case 'b' -> 1;
      case 'c' -> 2;
      case 'd' -> 3;
      case 'e' -> 4;
      case 'f' -> 5;
      case 'g' -> 6;
      case 'h' -> 7;
      case 'i' -> 8;
      case 'j' -> 9;
      default -> throw new GameException("잘못된 입력입니다.");
    };
  }
}
