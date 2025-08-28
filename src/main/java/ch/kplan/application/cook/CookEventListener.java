package ch.kplan.application.cook;

import ch.kplan.domain.cook.CookRepository;
import ch.kplan.domain.event.DomainEventPublisher;
import ch.kplan.domain.order.OrderPending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CookEventListener {

    private final CookRepository cookRepository;

    private final DomainEventPublisher domainEventPublisher;

    @Autowired
    public CookEventListener(CookRepository cookRepository, DomainEventPublisher domainEventPublisher) {
        this.cookRepository = cookRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @EventListener
    public void handle(OrderPending event) {
        cookRepository.findIdleCook()
            .ifPresent(cook -> cook.assignOrder(event.orderId(), domainEventPublisher));
    }
}
