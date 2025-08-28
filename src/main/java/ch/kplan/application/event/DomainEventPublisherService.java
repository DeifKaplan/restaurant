package ch.kplan.application.event;

import ch.kplan.domain.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DomainEventPublisherService implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DomainEventPublisherService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(Object doimainEvent) {
        applicationEventPublisher.publishEvent(doimainEvent);
    }
}
