package cleancode._day04_mission;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class Order {

  private final List<Item> items = new ArrayList<>();

  private String customerInfo;

  private int totalPrice = 0;

  public Order(String customerInfo) {
    this.customerInfo = customerInfo;
  }

  public boolean validateOrder() {
    if (noItems()) {
      System.out.println("주문 항목이 없습니다.");
      return false;
    }

    if (hasNoTotalPrice()) {
      System.out.println("올바르지 않은 총 가격입니다.");
      return false;
    }

    if (noCustomerInfo()) {
      System.out.println("사용자 정보가 없습니다.");
      return false;
    }

    return true;
  }

  private boolean noItems() {
    return items.isEmpty();
  }

  private boolean hasNoTotalPrice() {
    return totalPrice < 0;
  }

  private boolean noCustomerInfo() {
    return isNull(customerInfo);
  }

  public void addItem(Item item) {
    items.add(item);
    totalPrice += item.getPrice();
  }

  // FIXME: 임시로 customer 제거하기 위한 메서드
  public void clearCustomerInfo() {
    customerInfo = null;
  }
}
