package ch.kplan.application.order;

import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderCompleted;
import ch.kplan.domain.order.OrderInProgress;
import ch.kplan.domain.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventListener
    public void handle(OrderInProgress event) {
        final Order order = orderRepository.get(event.orderId());
        order.inProgress();
        orderRepository.update(order);
    }

    @EventListener
    public void handle(OrderCompleted event) {
        final Order order = orderRepository.get(event.orderId());
        order.completed();
        orderRepository.update(order);
    }
}
