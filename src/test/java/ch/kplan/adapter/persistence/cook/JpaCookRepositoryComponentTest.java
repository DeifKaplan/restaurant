package ch.kplan.adapter.persistence.cook;

import ch.kplan.domain.a;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.order.OrderId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaCookRepository.class, CookEntityMapper.class})
class JpaCookRepositoryComponentTest {

    @Autowired
    private JpaCookRepository repository;

    @Test
    void addAndGetCook() {
        Cook cook = a.Cook().build();

        repository.add(cook);

        assertThat(repository.get(cook.getId())).usingRecursiveComparison().isEqualTo(cook);
    }

    @Test
    void updateCook() {
        Cook cook = a.Cook().build();
        repository.add(cook);

        cook.assignOrder(new OrderId(99L), o -> {});
        repository.update(cook);
        assertThat(repository.get(cook.getId())).usingRecursiveComparison().isEqualTo(cook);
    }

    @Test
    void findIdleCook() {
        Cook idleCook = a.Cook().withOrderId(null).build();
        Cook busyCook = a.Cook().withOrderId(a.OrderId()).build();
        repository.add(idleCook);
        repository.add(busyCook);

        Optional<Cook> actual = repository.findIdleCook();

        assertThat(actual).contains(idleCook);
    }
}
