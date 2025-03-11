package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Biggner;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

  // 프로그램을 실행하는 책임만을 가짐
  public static void main(String[] args) {
    GameLevel gameLevel = new Biggner();
    InputHandler inputHandler = new ConsoleInputHandler();
    OutputHandler outputHandler = new ConsoleOutputHandler();

    // 인터페이스로 넣어주기 때문에 DIP 충족, 외부에서 주입시켜주어 DI 충족
    Minesweeper minesweeper = new Minesweeper(gameLevel,
                                              inputHandler,
                                              outputHandler);

    minesweeper.initialize();
    minesweeper.run();
  }

  /**
   * - DIP (Dependency Inversion Principle)
   * - DI (Dependency Injection)
   * - Ioc (Inversion of Control)
   */
}