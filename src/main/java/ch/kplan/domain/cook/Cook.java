package ch.kplan.domain.cook;

import ch.kplan.domain.event.DomainEventPublisher;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderInProgress;

import java.util.Objects;

public class Cook {

    private final CookId id;

    private OrderId orderId;

    private Cook(CookBuilder cookBuilder) {
        this.id = cookBuilder.id;
        this.orderId = cookBuilder.orderId;
    }

    public static Cook createNew(CookIdFactory cookIdFactory) {
        return new CookBuilder()
                .withId(cookIdFactory.nextId())
                .withOrderId(null)
                .build();
    }

    public void assignOrder(OrderId orderId, DomainEventPublisher domainEventPublisher) {
        if (this.orderId != null) {
            throw new IllegalStateException("Cook is already assigned to order: " + this.orderId);
        }
        this.orderId = orderId;
        domainEventPublisher.publish(new OrderInProgress(orderId));
    }

    public void completeAssignedOrder() {
        if (this.orderId == null) {
            throw new IllegalStateException("Cook has no assigned order to complete.");
        }
        this.orderId = null;
    }

    public CookId getId() {
        return id;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cook cook = (Cook) o;
        return Objects.equals(id, cook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cook{" +
                "cookId=" + id +
                ", orderId=" + orderId +
                '}';
    }

    public boolean isIdle() {
        return orderId == null;
    }

    public static class CookBuilder {
        private CookId id;

        private OrderId orderId;

        private CookBuilder() {

        }

        public CookBuilder withId(CookId id) {
            this.id = id;
            return this;
        }

        public CookBuilder withOrderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public Cook build() {
            return new Cook(this);
        }
    }
}
