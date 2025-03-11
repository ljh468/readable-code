package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class Minesweeper implements GameInitializable, GameRunable {

  private final GameBoard gameBoard;
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
    gameBoard = new GameBoard(gameLevel);
    this.inputHandler = inputHandler;
    this.outputHandler = outputHandler;
  }

  public void initialize() {
    gameBoard.initializeGame();
  }

  public void run() {
    outputHandler.showGameStartComments();

    while (true) {
      try {
        outputHandler.showBoard(gameBoard);

        if (doesUserWinTheGame()) {
          outputHandler.showGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          outputHandler.showGameLosingComment();
          break;
        }

        String callInput = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(callInput, userActionInput);
      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);
      } catch (Exception e) {
        // 예상치 못한 예외를 잡음
        outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
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
    outputHandler.showCommentForUserAction();
    return inputHandler.getUserInput();
  }

  private String getUserActionInputFromUser() {
    outputHandler.showCommentForSelectingCell();
    return inputHandler.getUserInput();
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
