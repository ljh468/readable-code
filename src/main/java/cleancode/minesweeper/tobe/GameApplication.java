package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Biggner;

public class GameApplication {

  // 프로그램을 실행하는 책임만을 가짐
  public static void main(String[] args) {

    //  바깥쪽에서 무엇이 돌아올지 모름
    Minesweeper minesweeper = new Minesweeper(new Biggner());
    minesweeper.initialize();
    minesweeper.run();
  }
}