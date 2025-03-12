package cleancode._day07_mission.tobe.model;

import java.util.List;

public class PassOptions {

  private final List<StudyCafePass> passes;

  public PassOptions(List<StudyCafePass> passes) {
    this.passes = passes;
  }

  public List<StudyCafePass> getPasses() {
    return passes;
  }
}