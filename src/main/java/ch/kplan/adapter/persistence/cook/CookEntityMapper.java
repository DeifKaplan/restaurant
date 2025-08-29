package ch.kplan.adapter.persistence.cook;

import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.Cook.CookBuilder;
import ch.kplan.domain.cook.CookId;
import ch.kplan.domain.order.OrderId;
import ch.kplan.util.DomainObjectHelper;
import org.springframework.stereotype.Component;

@Component
public class CookEntityMapper {
    public CookEntity toEntity(Cook cook) {
        Long orderId = cook.getOrderId() != null ? cook.getOrderId().value() : null;
        return new CookEntity(cook.getId().value(), orderId);
    }

    public Cook toDomain(CookEntity entity) {
        CookId cookId = new CookId(entity.getId());
        OrderId orderId = entity.getOrderId() != null ? new OrderId(entity.getOrderId()) : null;
        return DomainObjectHelper.createInstance(CookBuilder.class)
                .withId(cookId)
                .withOrderId(orderId)
                .build();
    }
}
