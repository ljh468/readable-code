package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

  private final GameBoard gameBoard;
  private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
  private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(GameLevel gameLevel) {
    gameBoard = new GameBoard(gameLevel);
  }

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
    int selectedColIndex = boardIndexConverter.getSelectedColIndex(callInput, gameBoard.getColSize());
    int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(callInput, gameBoard.getRowSize());

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
}
