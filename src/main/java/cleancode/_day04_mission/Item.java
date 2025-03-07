package cleancode._day04_mission;

public class Item {

  private final long id;

  private final String name;

  private final int price;

  public Item(long id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public int getPrice() {
    return price;
  }
}
