## DAY02 미션
1. 아래 코드와 설명을 보고, [섹션 3. 논리, 사고의 흐름]에서 이야기하는 내용을 중심으로 읽기 좋은 코드로 리팩토링해 봅시다.
2. SOLID에 대하여 자기만의 언어로 정리해 봅시다.

<br>

---

## 미션 수행

- 사용자가 생성한 '주문'이 유효한지를 검증하는 메서드.
- Order는 주문 객체이고, 필요하다면 Order에 추가적인 메서드를 만들어도 된다. (Order 내부의 구현을 구체적으로 할 필요는 없다.)
- 필요하다면 메서드를 추출할 수 있다.

### asis
```java
public boolean validateOrder(Order order) {
    if (order.getItems().size() == 0) {
        log.info("주문 항목이 없습니다.");
        return false;
    } else {
        if (order.getTotalPrice() > 0) {
            if (!order.hasCustomerInfo()) {
                log.info("사용자 정보가 없습니다.");
                return false;
            } else {
                return true;
            }
        } else if (!(order.getTotalPrice() > 0)) {
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }
    }
    return true;
}
```

## tobe

### OrderMain 클래스
```java
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
```

### Order 클래스
```java
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
```

### Item 클래스
```java
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
```
---

## SOLID에 대해 정리해보기
SOLID는 로버트 c.마틴이 객체지향적 코드를 위해 지켜야할 원칙을 정의한 것입니다.
- `SRP(Single Responsibility Principle)`는 하나의 클래스가 하나의 책임만을 가지는 것을 말합니다.
- `OCP(Open Closed Principle)`은 기존 코드의 변경은 닫혀있고 확장에는 열려있어야 한다는 의미입니다.
- `LSP(Liskov Substitution Principle)`는 하위 타입의 클래스는 상위 타입을 대체할 수 있어야 한다라는 말입니다.
- `ISP(Interface Segregation Principle)`는 인터페이스는 메서드(기능)를 강제하는데, 만약 하위 구현체에 필요없는 메서드를 구현해야하는 경우 새로운 인터페이스를 생성해야 한다는 말입니다.
- `DIP(Dependency Inversion Principle)`는 직접 구현체를 의존하는게 아니라 추상화된 인터페이스나 추상클래스를 의존해야 한다는 것입니다.

모두 지키는 것은 어렵지만, 객체지향 코드를 작성하려면 항상 이러한 원칙들을 고려해서 설계하려고 노력해야합니다.