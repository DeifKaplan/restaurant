package ch.kplan.application;

import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderIdFactory;
import ch.kplan.domain.order.OrderRepository;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.WaiterId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderIdFactory orderIdFactory;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderIdFactory orderIdFactory, OrderRepository orderRepository) {
        this.orderIdFactory = orderIdFactory;
        this.orderRepository = orderRepository;
    }

    public void placeOrder(TableId tableId, WaiterId waiterId, MealIds mealIds) {
        Order order = Order.createNew(orderIdFactory, waiterId, tableId, mealIds);
        orderRepository.add(order);
    }

    public Optional<Order> searchNextPendingOrder() {
        return orderRepository.searchNextPendingOrder();
    }

    public Order getOrder(OrderId orderId) {
        return orderRepository.get(orderId);
    }
}