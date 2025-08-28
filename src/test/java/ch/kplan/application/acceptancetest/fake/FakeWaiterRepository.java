package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.waiter.Waiter;
import ch.kplan.domain.waiter.WaiterId;
import ch.kplan.domain.waiter.WaiterRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class FakeWaiterRepository implements WaiterRepository, StatefulFake {

    private final Set<Waiter> fakeWaiters = new HashSet<>();

    @Override
    public Waiter get(WaiterId id) {
        return fakeWaiters.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Waiter not found: " + id));
    }

    @Override
    public void add(Waiter waiter) {
        fakeWaiters.add(waiter);
    }

    @Override
    public void reset() {
        fakeWaiters.clear();
    }
}
