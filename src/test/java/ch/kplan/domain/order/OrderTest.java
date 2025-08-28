package ch.kplan.domain.order;

import ch.kplan.domain.a;
import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.WaiterId;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void createNew() {
        WaiterId waiterId = a.WaiterId();
        TableId tableId = a.TableId();
        MealIds mealIds = a.MealIds(a.MealId());
        OrderId orderId = a.OrderId();
        Order actual = Order.createNew(() -> orderId, waiterId, tableId, mealIds);

        assertThat(actual).usingRecursiveComparison().isEqualTo(a.Order()
                .withId(orderId)
                .withWaiterId(waiterId)
                .withTableId(tableId)
                .withMealIds(mealIds)
                .withOrdered(actual.getOrdered())
                .withStatus(OrderStatus.PENDING));
    }

    @Test
    void pending_to_inProgress() {
        Order sut = a.Order().withStatus(OrderStatus.PENDING).build();

        sut.inProgress();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }

    @Test
    void inProgress_to_inProgress() {
        Order sut = a.Order().withStatus(OrderStatus.IN_PROGRESS).build();

        assertThatThrownBy(sut::inProgress).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void completed_to_inProgress() {
        Order sut = a.Order().withStatus(OrderStatus.COMPLETED).withDelivered(ZonedDateTime.now()).build();

        assertThatThrownBy(sut::inProgress).isInstanceOf(IllegalStateException.class);
        assertThat(sut.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    void pending_to_completed() {
        Order sut = a.Order().withStatus(OrderStatus.PENDING).build();

        assertThatThrownBy(sut::completed).isInstanceOf(IllegalStateException.class);
        assertThat(sut.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void inProgress_to_completed() {
        Order sut = a.Order().withStatus(OrderStatus.IN_PROGRESS).build();

        sut.completed();

        assertThat(sut.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(sut.getDelivered()).isNotNull();
    }

    @Test
    void completed_to_completed() {
        Order sut = a.Order().withStatus(OrderStatus.COMPLETED).withDelivered(ZonedDateTime.now()).build();

        assertThatThrownBy(sut::completed).isInstanceOf(IllegalStateException.class);
    }
}

