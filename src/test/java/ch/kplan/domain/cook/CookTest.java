package ch.kplan.domain.cook;

import ch.kplan.domain.a;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderInProgress;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CookTest {

    @Test
    void idleCook_assignOrder() {
        Cook sut = a.Cook().withOrderId(null).build();
        final List<Object> publishedEvents = new ArrayList<>();
        OrderId orderId = a.OrderId();

        sut.assignOrder(orderId, publishedEvents::add);

        assertThat(publishedEvents).contains(new OrderInProgress(orderId));
        assertThat(sut.getOrderId()).isEqualTo(orderId);
    }

    @Test
    void busyCook_assignOrder() {
        OrderId originalOrderId = a.OrderId();
        Cook sut = a.Cook().withOrderId(originalOrderId).build();
        OrderId newOrderId = a.OrderId();

        assertThatThrownBy(() -> sut.assignOrder(newOrderId, (_) -> fail())).isInstanceOf(IllegalStateException.class);
        assertThat(sut.getOrderId()).isEqualTo(originalOrderId);
    }

    @Test
    void idleCook_completeAssignedOrder() {
        Cook sut = a.Cook().withOrderId(null).build();

        assertThatThrownBy(sut::completeAssignedOrder).isInstanceOf(IllegalStateException.class);

        assertThat(sut.isIdle()).isTrue();
    }

    @Test
    void busyCook_completeAssignedOrder() {
        OrderId orderId = a.OrderId();
        Cook sut = a.Cook().withOrderId(orderId).build();

        sut.completeAssignedOrder();

        assertThat(sut.isIdle()).isTrue();
    }
}