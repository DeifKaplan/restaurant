package ch.kplan.domain.order;

import java.util.Optional;

public interface OrderRepository {
    void add(Order order);

    Order get(OrderId orderId);

    void update(Order order);

    Optional<Order> searchNextPendingOrder();
}
