package cleancode._day04_mission;

public class OrderMain {

  public static void main(String[] args) {
    // 주문 아이템이 없는 경우
    Order order = new Order("custommer01");
    order.validateOrder();

    // 아이템이 올바르지 않은 가격인 경우 (올바르지 않은 가격)
    order.addItem(new Item(1, "item01", -100));
    order.validateOrder();

    // 사용자 정보가 없는 경우
    Order order2 = new Order("custommer02");
    order2.clearCustomerInfo();
    order2.addItem(new Item(1, "item02", 100));
    order2.validateOrder();
  }
}
