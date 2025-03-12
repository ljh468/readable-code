package cleancode._day07_mission.tobe.model;

public class StudyCafeLockerPass {

  private final StudyCafePassType passType;

  private final int duration;

  private final int price;

  private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
    this.passType = passType;
    this.duration = duration;
    this.price = price;
  }

  public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
    return new StudyCafeLockerPass(passType, duration, price);
  }

  public StudyCafePassType getPassType() {
    return passType;
  }

  public int getDuration() {
    return duration;
  }

  public int getPrice() {
    return price;
  }

  public String display() {
    return switch (passType) {
      case HOURLY -> String.format("%s시간권 - %d원", duration, price);
      case WEEKLY, FIXED -> String.format("%s주권 - %d원", duration, price);
    };
  }

}
