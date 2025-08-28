package ch.kplan.application.acceptancetest;

import ch.kplan.application.OrderService;
import ch.kplan.application.acceptancetest.fake.*;
import ch.kplan.application.cook.CookEventListener;
import ch.kplan.application.cook.CookService;
import ch.kplan.application.event.DomainEventPublisherService;
import ch.kplan.application.order.OrderEventListener;
import ch.kplan.application.waiter.WaiterService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {
        WaiterService.class,
        OrderService.class,
        FakeOrderIdFactory.class,
        FakeOrderRepository.class,
        Given.class,
        FakeLoggedInWaiterService.class,
        FakeWaiterRepository.class,
        FakeTableRepository.class,
        DomainEventPublisherService.class,
        FakeCookRepository.class,
        FakeLoggedInCookService.class,
        CookEventListener.class,
        OrderEventListener.class,
        CookService.class
})
@ExtendWith(AcceptanceTestExtension.class)
public @interface AcceptanceTest {
}
