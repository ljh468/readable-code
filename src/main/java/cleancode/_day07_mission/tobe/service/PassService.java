package cleancode._day07_mission.tobe.service;

import cleancode._day07_mission.tobe.io.StudyCafeFileHandler;
import cleancode._day07_mission.tobe.model.PassOptions;
import cleancode._day07_mission.tobe.model.StudyCafeLockerPass;
import cleancode._day07_mission.tobe.model.StudyCafePass;
import cleancode._day07_mission.tobe.model.StudyCafePassType;

import java.util.List;

public class PassService {

  private final StudyCafeFileHandler fileHandler;

  public PassService() {
    this.fileHandler = new StudyCafeFileHandler();
  }

  public PassOptions getPassOptions(StudyCafePassType passType) {
    List<StudyCafePass> allPasses = fileHandler.readStudyCafePasses();

    List<StudyCafePass> filteredPasses = allPasses.stream()
                                                  .filter(pass -> pass.getPassType() == passType)
                                                  .toList();

    return new PassOptions(filteredPasses);
  }

  public StudyCafeLockerPass findCompatibleLockerPass(StudyCafePass selectedPass) {
    List<StudyCafeLockerPass> lockerPasses = fileHandler.readLockerPasses();

    return lockerPasses.stream()
                       .filter(option ->
                                   option.getPassType() == selectedPass.getPassType()
                                       && option.getDuration() == selectedPass.getDuration()
                       )
                       .findFirst()
                       .orElse(null);
  }
}
