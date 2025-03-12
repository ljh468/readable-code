package cleancode.minesweeper.tobe;

import cleancode.minesweeper.position.CellPosition;
import cleancode.minesweeper.position.RelativePosition;
import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {

  public static final int LAND_MINE_COUNT = 10;

  private final Cell[][] board;

  private final int landMineCount;

  public GameBoard(GameLevel gameLevel) {
    board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];
    landMineCount = gameLevel.getLandMineCount();
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();
  }

  public void openAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  public boolean isLandMineCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isLandMine();
  }

  public boolean isAllCellChecked() {
    return Arrays.stream(board) // Stream<Cell[]>
                 .flatMap(Arrays::stream) // Stream<Stream<Cell>> -> Stream<Cell>
                 .allMatch(Cell::isChecked); // Cell에 sign이 같은지 물어봐야함
  }

  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int colSize = getColSize();
    int rowSize = getRowSize();
    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
        || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public void initializeGame() {
    int rowSize = getRowSize();
    int colSize = getColSize();

    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        board[row][col] = new EmptyCell();
      }
    }

    for (int i = 0; i < LAND_MINE_COUNT; i++) {
      int landMineCol = new Random().nextInt(colSize);
      int landMineRow = new Random().nextInt(rowSize);
      board[landMineRow][landMineCol] = new LandMineCell();
    }

    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        CellPosition cellPosition = CellPosition.of(row, col);

        if (isLandMineCellAt(cellPosition)) {
          continue;
        }
        int count = countNearbyLandMines(cellPosition);
        if (count == 0) {
          continue;
        }
        board[row][col] = new NumberCell(count);
      }
    }
  }

  public String getSign(CellPosition cellPosition) {
    return findCell(cellPosition).getSign();
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  public void openSurroundedCells(CellPosition cellPosition) {
    if (cellPosition.isRowIndexMoreThanOrEqual(getRowSize())
        || cellPosition.isColIndexMoreThanOrEqual(getColSize())
    ) {
      return;
    }

    if (isOpenedCell(cellPosition)) {
      return;
    }

    if (isLandMineCellAt(cellPosition)) {
      return;
    }

    openAt(cellPosition);

    if (doesCellHaveLandMineCount(cellPosition)) {
      return;
    }

    List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition,
                                                                          getRowSize(),
                                                                          getColSize());
    surroundedPositions.forEach(this::openSurroundedCells);

  }

  private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
    return findCell(cellPosition).hasLandMineCount();
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    return findCell(cellPosition).isOpened();
  }

  public int countNearbyLandMines(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    return (int) calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                                                                             .filter(this::isLandMineCellAt)
                                                                             .count();
  }

  private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
    return RelativePosition.SURROUNDED_POSITIONS.stream()
                                                .filter(cellPosition::canCalculatePositionBy)
                                                .map(cellPosition::calculatePositionBy)
                                                .filter(position -> position.isRowIndexLessThan(rowSize))
                                                .filter(position -> position.isColIndexLessThan(colSize))
                                                .toList();
  }
}
