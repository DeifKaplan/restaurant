package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class FakeOrderRepository implements OrderRepository, StatefulFake {

    private final Set<Order> fakeOrders = new HashSet<>();

    @Override
    public void add(Order order) {
        fakeOrders.add(order);
    }

    @Override
    public Order get(OrderId orderId) {
        return fakeOrders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    @Override
    public void update(Order order) {
        Order existingOrder = get(order.getId());// Ensure the order exists
        fakeOrders.remove(existingOrder);
        fakeOrders.add(order);
    }

    @Override
    public Optional<Order> searchNextPendingOrder() {
        return fakeOrders.stream()
                .filter(Order::isPending)
                .min(Comparator.comparing(Order::getOrdered));
    }

    @Override
    public void reset() {
        fakeOrders.clear();
    }
}
