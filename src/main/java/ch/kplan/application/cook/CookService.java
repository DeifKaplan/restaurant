package ch.kplan.application.cook;

import ch.kplan.application.MealService;
import ch.kplan.application.OrderService;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.CookRepository;
import ch.kplan.domain.cook.LoggedInCookService;
import ch.kplan.domain.event.DomainEventPublisher;
import ch.kplan.domain.meal.Meals;
import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookService  {

    private final DomainEventPublisher domainEventPublisher;

    private final CookRepository cookRepository;

    private final OrderService orderService;

    private final MealService mealService;

    private final LoggedInCookService loggedInCookService;

    @Autowired
    public CookService(DomainEventPublisher domainEventPublisher, CookRepository cookRepository, OrderService orderService, MealService mealService, LoggedInCookService loggedInCookService) {
        this.domainEventPublisher = domainEventPublisher;
        this.cookRepository = cookRepository;
        this.orderService = orderService;
        this.mealService = mealService;
        this.loggedInCookService = loggedInCookService;
    }

    public void cookCurrentOrder() {
        Cook cook = cookRepository.get(loggedInCookService.getLoggedInCookId());
        Order order = orderService.getOrder(cook.getOrderId());
        Meals meals = mealService.getMealsById(order.getMealIds());
        meals.forEach(meal -> meal.cook(domainEventPublisher));
    }

    public void orderCompleted() {
        Cook cook = cookRepository.get(loggedInCookService.getLoggedInCookId());
        domainEventPublisher.publish(new OrderCompleted(cook.getOrderId()));
        cook.completeAssignedOrder();
        orderService.searchNextPendingOrder().map(Order::getId).ifPresent(orderId -> cook.assignOrder(orderId, domainEventPublisher));
        cookRepository.update(cook);
    }


}
