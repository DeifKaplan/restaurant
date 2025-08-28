package ch.kplan.application.acceptancetest;

import ch.kplan.application.acceptancetest.fake.FakeLoggedInCookService;
import ch.kplan.application.acceptancetest.fake.FakeLoggedInWaiterService;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.CookRepository;
import ch.kplan.domain.order.Order;
import ch.kplan.domain.order.OrderRepository;
import ch.kplan.domain.table.Table;
import ch.kplan.domain.table.TableRepository;
import ch.kplan.domain.waiter.Waiter;
import ch.kplan.domain.waiter.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Given {

    private final FakeLoggedInWaiterService fakeLoggedInWaiterService;

    private final FakeLoggedInCookService fakeLoggedInCookService;

    private final WaiterRepository waiterRepository;

    private final TableRepository tableRepository;

    private final CookRepository cookRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public Given(FakeLoggedInWaiterService fakeLoggedInWaiterService, FakeLoggedInCookService fakeLoggedInCookService, WaiterRepository waiterRepository, TableRepository tableRepository, CookRepository cookRepository, OrderRepository orderRepository) {
        this.fakeLoggedInWaiterService = fakeLoggedInWaiterService;
        this.fakeLoggedInCookService = fakeLoggedInCookService;
        this.waiterRepository = waiterRepository;
        this.tableRepository = tableRepository;
        this.cookRepository = cookRepository;
        this.orderRepository = orderRepository;
    }

    public Given withLoggedInWaiter(Waiter waiter) {
        waiterRepository.add(waiter);
        fakeLoggedInWaiterService.setLoggedInWaiterId(waiter.getId());
        return this;
    }


    public Given withTables(Table ... tables) {
        for (Table table : tables) {
            tableRepository.add(table);
        }
        return this;
    }

    public Given withLoggedInCook(Cook cook) {
        cookRepository.add(cook);
        fakeLoggedInCookService.setLoggedInCookId(cook.getId());
        return this;
    }

    public Given withOrders(Order ... orders) {
        for (Order order : orders) {
            orderRepository.add(order);
        }
        return this;
    }
}
