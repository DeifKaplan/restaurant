package ch.kplan.domain.order;

import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.WaiterId;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Order {

    private final OrderId id;

    private final TableId tableId;

    private final WaiterId waiterId;

    private OrderStatus status;

    private final MealIds mealIds;

    private final ZonedDateTime ordered;

    private ZonedDateTime delivered;

    public static Order createNew(final OrderIdFactory orderIdFactory,
                                  final WaiterId waiterId,
                                  final TableId tableId,
                                  final MealIds mealIds) {
        return new OrderBuilder()
                .withId(orderIdFactory.nextId())
                .withTableId(tableId)
                .withWaiterId(waiterId)
                .withStatus(OrderStatus.PENDING)
                .withMealIds(mealIds)
                .withOrdered(ZonedDateTime.now())
                .build();
    }

    private Order(OrderBuilder orderBuilder) {
        id = orderBuilder.id;
        tableId = orderBuilder.tableId;
        waiterId = orderBuilder.waiterId;
        status = orderBuilder.status;
        mealIds = orderBuilder.mealIds;
        ordered = orderBuilder.ordered;
        delivered = orderBuilder.delivered;
    }

    public OrderId getId() {
        return id;
    }

    public TableId getTableId() {
        return tableId;
    }

    public WaiterId getWaiterId() {
        return waiterId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public MealIds getMealIds() {
        return mealIds;
    }

    public ZonedDateTime getOrdered() {
        return ordered;
    }

    public ZonedDateTime getDelivered() {
        return delivered;
    }

    public void inProgress() {
        status = status.inProgress();
    }

    public void completed() {
        status = status.completed();
        delivered = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", waiterId=" + waiterId +
                ", status=" + status +
                ", mealIds=" + mealIds +
                ", ordered=" + ordered +
                ", delivered=" + delivered +
                '}';
    }

    public boolean isPending() {
        return status.isPending();
    }

    public static class OrderBuilder {
        private OrderId id;
        private TableId tableId;
        private WaiterId waiterId;
        private OrderStatus status;
        private MealIds mealIds;
        private ZonedDateTime ordered;
        private ZonedDateTime delivered;

        private OrderBuilder() {
        }

        public OrderBuilder withId(OrderId id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withTableId(TableId tableId) {
            this.tableId = tableId;
            return this;
        }

        public OrderBuilder withWaiterId(WaiterId waiterId) {
            this.waiterId = waiterId;
            return this;
        }

        public OrderBuilder withStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder withMealIds(MealIds mealIds) {
            this.mealIds = mealIds;
            return this;
        }

        public OrderBuilder withOrdered(ZonedDateTime ordered) {
            this.ordered = ordered;
            return this;
        }

        public OrderBuilder withDelivered(ZonedDateTime delivered) {
            this.delivered = delivered;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
