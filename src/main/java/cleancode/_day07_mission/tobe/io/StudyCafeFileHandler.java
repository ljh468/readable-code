package cleancode._day07_mission.tobe.io;

import cleancode._day07_mission.tobe.model.StudyCafeLockerPass;
import cleancode._day07_mission.tobe.model.StudyCafePass;
import cleancode._day07_mission.tobe.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StudyCafeFileHandler {

  public List<StudyCafePass> readStudyCafePasses() {
    return readFromFile(
        "src/main/resources/cleancode/studycafe/pass-list.csv",
        values -> StudyCafePass.of(
            StudyCafePassType.valueOf(values[0]),
            Integer.parseInt(values[1]),
            Integer.parseInt(values[2]),
            Double.parseDouble(values[3])
        )
    );
  }

  public List<StudyCafeLockerPass> readLockerPasses() {
    return readFromFile(
        "src/main/resources/cleancode/studycafe/locker.csv",
        values -> StudyCafeLockerPass.of(
            StudyCafePassType.valueOf(values[0]),
            Integer.parseInt(values[1]),
            Integer.parseInt(values[2])
        )
    );
  }

  private <T> List<T> readFromFile(String filePath, Function<String[], T> mapper) {
    try {
      List<String> lines = Files.readAllLines(Paths.get(filePath));
      List<T> result = new ArrayList<>();

      for (String line : lines) {
        String[] values = line.split(",");
        result.add(mapper.apply(values));
      }

      return result;
    } catch (IOException e) {
      throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
    }
  }
}