package cleancode.minesweeper.tobe;

public class GameApplication {

  // 프로그램을 실행하는 책임만을 가짐
  public static void main(String[] args) {
    Minesweeper minesweeper = new Minesweeper();
    minesweeper.run();
  }
}