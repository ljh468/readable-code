package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

  public static final char BASE_CHAR_FOR_COL = 'a';

  public int getSelectedRowIndex(String callInput, int rowSize) {
    String cellInputRow = callInput.substring(1);
    return convertRowFrom(cellInputRow, rowSize);
  }

  public int getSelectedColIndex(String callInput, int colSize) {
    char cellInputCol = callInput.charAt(0);
    return convertColFrom(cellInputCol, colSize); // cellInput으로 부터 변환
  }

  private int convertRowFrom(String cellInputRow, int rowSize) {
    int rowIndex = Integer.parseInt(cellInputRow) - 1;
    if (rowIndex < 0 || rowIndex >= rowSize) {
      throw new GameException("잘못된 입력입니다.");
    }
    return rowIndex;
  }

  private int convertColFrom(char cellInputCol, int colSize) {
    int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
    if (colIndex < 0 || colIndex >= colSize) {
      throw new GameException("잘못된 입력입니다.");
    }
    return colIndex;
  }
}
