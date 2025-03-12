package cleancode._day07_mission.tobe.model;

public class PassOrder {

  private final StudyCafePass pass;

  private final StudyCafeLockerPass lockerPass;

  private PassOrder(StudyCafePass pass, StudyCafeLockerPass lockerPass) {
    this.pass = pass;
    this.lockerPass = lockerPass;
  }

  public static PassOrder of(StudyCafePass pass, StudyCafeLockerPass lockerPass) {
    return new PassOrder(pass, lockerPass);
  }

  public StudyCafePass getPass() {
    return pass;
  }

  public StudyCafeLockerPass getLockerPass() {
    return lockerPass;
  }

  public int calculateTotalPrice() {
    int discountPrice = calculateDiscountPrice();
    int lockerPrice = lockerPass != null ? lockerPass.getPrice() : 0;

    return pass.getPrice() - discountPrice + lockerPrice;
  }

  public int calculateDiscountPrice() {
    return (int) (pass.getPrice() * pass.getDiscountRate());
  }

  public boolean hasDiscount() {
    return calculateDiscountPrice() > 0;
  }
}