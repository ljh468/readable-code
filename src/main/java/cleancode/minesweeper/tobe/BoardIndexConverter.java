package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

  public static final char BASE_CHAR_FOR_COL = 'a';

  public int getSelectedRowIndex(String callInput) {
    String cellInputRow = callInput.substring(1);
    return convertRowFrom(cellInputRow);
  }

  public int getSelectedColIndex(String callInput) {
    char cellInputCol = callInput.charAt(0);
    return convertColFrom(cellInputCol); // cellInput으로 부터 변환
  }

  private int convertRowFrom(String cellInputRow) {
    int rowIndex = Integer.parseInt(cellInputRow) - 1;
    if (rowIndex < 0) {
      throw new GameException("잘못된 입력입니다.");
    }
    return rowIndex;
  }

  private int convertColFrom(char cellInputCol) {
    int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
    if (colIndex < 0) {
      throw new GameException("잘못된 입력입니다.");
    }
    return colIndex;
  }
}
