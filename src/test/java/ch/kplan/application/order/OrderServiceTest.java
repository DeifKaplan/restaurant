package ch.kplan.application.order;

import ch.kplan.application.OrderService;
import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderIdFactory;
import ch.kplan.domain.order.OrderRepository;
import ch.kplan.domain.order.OrderStatus;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.WaiterId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderIdFactory orderIdFactoryMock;

    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private OrderService sut;

    /**
     * So goes it not
     */
    @Test
    void placeOrder() {
        TableId tableIdMock = mock(TableId.class);
        WaiterId waiterIdMock = mock(WaiterId.class);
        MealIds mealIdsMock = mock(MealIds.class);
        OrderId orderId = mock(OrderId.class);
        when(orderIdFactoryMock.nextId()).thenReturn(orderId);

        sut.placeOrder(tableIdMock, waiterIdMock, mealIdsMock);

        verify(orderRepositoryMock).add(Mockito.argThat(order ->
            order.getId().equals(orderId) &&
            order.getTableId().equals(tableIdMock) &&
            order.getWaiterId().equals(waiterIdMock) &&
            order.getMealIds().equals(mealIdsMock) &&
            order.getStatus().equals(OrderStatus.PENDING) &&
            order.getOrdered() != null &&
            order.getDelivered() == null
        ));
    }
}
