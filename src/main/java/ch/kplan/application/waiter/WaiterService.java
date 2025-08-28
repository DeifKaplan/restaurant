package ch.kplan.application.waiter;

import ch.kplan.application.OrderService;
import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.LoggedInWaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaiterService {

    private final OrderService orderService;

    private final LoggedInWaiterService loggedInWaiterService;

    @Autowired
    public WaiterService(OrderService orderService, LoggedInWaiterService loggedInWaiterService) {
        this.orderService = orderService;
        this.loggedInWaiterService = loggedInWaiterService;
    }

    public void placeOrder(TableId tableId, MealIds mealIds) {
        orderService.placeOrder(tableId, loggedInWaiterService.getLoggedInWaiterId(), mealIds);
    }
}
