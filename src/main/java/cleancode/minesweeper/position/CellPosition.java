package cleancode.minesweeper.position;

import java.util.Objects;

public class CellPosition {

  private final int rowIndex;

  private final int colIndex;

  private CellPosition(int rowIndex, int colIndex) {
    if (rowIndex < 0 || colIndex < 0) {
      throw new IllegalArgumentException("올바르지 않은 좌표입니다."); // 개발자가 확인하는 예외
    }
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  public static CellPosition of(int rowIndex, int colIndex) {
    return new CellPosition(rowIndex, colIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    CellPosition that = (CellPosition) o;
    return rowIndex == that.rowIndex && colIndex == that.colIndex;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowIndex, colIndex);
  }

  public boolean isRowIndexMoreThanOrEqual(int rowSize) {
    return this.rowIndex >= rowSize;
  }

  public boolean isColIndexMoreThanOrEqual(int colIndex) {
    return this.colIndex >= colIndex;
  }

  public int getColIndex() {
    return this.colIndex;
  }

  public int getRowIndex() {
    return this.rowIndex;
  }

  public boolean canCalculatePositionBy(RelativePosition relativePosition) {
    return this.rowIndex + relativePosition.getDeltaRow() >= 0
        && this.colIndex + relativePosition.getDeltaCol() >= 0;
  }

  public CellPosition calculatePositionBy(RelativePosition relativePosition) {
    if (canCalculatePositionBy(relativePosition)) {
      return CellPosition.of(
          this.rowIndex + relativePosition.getDeltaRow(),
          this.colIndex + relativePosition.getDeltaCol()
      );
    }
    throw new IllegalArgumentException("움직일 수 있는 좌표가 아닙니다.");
  }

  public boolean isRowIndexLessThan(int rowSize) {
    return this.rowIndex < rowSize;
  }

  public boolean isColIndexLessThan(int colSize) {
    return this.colIndex < colSize;
  }
}
