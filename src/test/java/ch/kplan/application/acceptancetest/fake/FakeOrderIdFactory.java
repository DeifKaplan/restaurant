package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderIdFactory;
import org.springframework.stereotype.Service;

@Service
public class FakeOrderIdFactory implements OrderIdFactory, StatefulFake {

    private long orderIdCounter = 1L;

    @Override
    public OrderId nextId() {
        return new OrderId(orderIdCounter++);
    }

    @Override
    public void reset() {
        orderIdCounter = 1L;
    }
}
