package cleancode.minesweeper.tobe.cell;

public class EmptyCell implements Cell {

  private static final String EMPTY_SIGN = "■";

  private final CellState Cell2State = CellState.initialize();

  @Override
  public boolean isLandMine() {
    return false;
  }

  @Override
  public boolean hasLandMineCount() {
    return false;
  }

  @Override
  public void flag() {
    Cell2State.flag();
  }

  @Override
  public void open() {
    Cell2State.open();
  }

  @Override
  public boolean isChecked() {
    return Cell2State.isChecked();
  }

  @Override
  public boolean isOpened() {
    return Cell2State.isOpened();
  }

  @Override
  public String getSign() {
    if (Cell2State.isOpened()) {
      return EMPTY_SIGN;
    }

    if (Cell2State.isFlagged()) {
      return FLAG_SIGN;
    }
    return UNCHECKED_SIGN;
  }
}
