package ch.kplan.domain.event;

public interface DomainEventPublisher {

    void publish(Object doimainEvent);
}
