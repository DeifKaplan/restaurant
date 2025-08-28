package ch.kplan.application.order;

import ch.kplan.application.acceptancetest.AcceptanceTest;
import ch.kplan.application.acceptancetest.Given;
import ch.kplan.application.cook.CookService;
import ch.kplan.application.waiter.WaiterService;
import ch.kplan.domain.a;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.CookRepository;
import ch.kplan.domain.event.DomainEventPublisher;
import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderPending;
import ch.kplan.domain.order.OrderRepository;
import ch.kplan.domain.order.OrderStatus;
import ch.kplan.domain.table.Table;
import ch.kplan.domain.waiter.Waiter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@AcceptanceTest
class OrderAcceptanceTest {

    @Autowired
    private WaiterService waiterService;

    @Autowired
    private Given given;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private CookService cookService;

    @Test
    void placeOrder() {
        Waiter waiter = a.Waiter().build();
        Table table = a.Table().build();
        given
                .withLoggedInWaiter(waiter)
                .withTables(table);
        waiterService.placeOrder(table.getId(), a.MealIds(a.MealId()));

        Order actual = orderRepository.searchNextPendingOrder().orElseThrow();
        Order expected = a.Order()
                .withWaiterId(waiter.getId())
                .withTableId(table.getId())
                .withStatus(OrderStatus.PENDING)
                .withId(actual.getId())
                .withOrdered(actual.getOrdered())
                .withDelivered(null)
                .build();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void assignOrderOnIdleCook() {
        Cook cook = a.Cook()
                .withOrderId(null)
                .build();
        Order order = a.Order()
                .withDelivered(null)
                .withStatus(OrderStatus.PENDING)
                .withMealIds(a.MealIds(a.MealId()))
                .build();
        given
                .withLoggedInCook(cook)
                .withOrders(order);

        domainEventPublisher.publish(new OrderPending(order.getId()));

        Order actual = orderRepository.get(order.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThat(cookRepository.get(cook.getId()).getOrderId()).isEqualTo(order.getId());
    }

    @Test
    void assignNoOrderOnBusyCook() {
        Order existingOrder = a.Order()
                .withDelivered(null)
                .withStatus(OrderStatus.IN_PROGRESS)
                .withMealIds(a.MealIds(a.MealId()))
                .build();
        Order newOrder = a.Order()
                .withDelivered(null)
                .withStatus(OrderStatus.PENDING)
                .withMealIds(a.MealIds(a.MealId()))
                .build();
        Cook cook = a.Cook()
                .withOrderId(existingOrder.getId())
                .build();
        given
                .withLoggedInCook(cook)
                .withOrders(existingOrder, newOrder);

        domainEventPublisher.publish(new OrderPending(existingOrder.getId()));

        Order actual = orderRepository.get(newOrder.getId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(cookRepository.get(cook.getId()).getOrderId()).isEqualTo(existingOrder.getId());
    }

    @Test
    void completeOrderWithPendingOrder() {
        Order existingOrder = a.Order()
                .withDelivered(null)
                .withStatus(OrderStatus.IN_PROGRESS)
                .withMealIds(a.MealIds(a.MealId()))
                .build();
        Order newOrder = a.Order()
                .withDelivered(null)
                .withStatus(OrderStatus.PENDING)
                .withMealIds(a.MealIds(a.MealId()))
                .build();
        Cook cook = a.Cook()
                .withOrderId(existingOrder.getId())
                .build();
        given
                .withLoggedInCook(cook)
                .withOrders(existingOrder, newOrder);

        cookService.orderCompleted();

        assertThat(orderRepository.get(existingOrder.getId()).getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(orderRepository.get(newOrder.getId()).getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThat(cookRepository.get(cook.getId()).getOrderId()).isEqualTo(newOrder.getId());
    }
}
