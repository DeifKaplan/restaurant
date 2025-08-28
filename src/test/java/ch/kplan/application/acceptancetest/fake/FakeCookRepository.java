package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.CookId;
import ch.kplan.domain.cook.CookRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class FakeCookRepository implements CookRepository, StatefulFake {

    private final Set<Cook> fakeCooks = new HashSet<>();

    @Override
    public Optional<Cook> findIdleCook() {
        return fakeCooks.stream()
                .filter(Cook::isIdle)
                .findFirst();
    }

    @Override
    public Cook get(CookId cookId) {
        return fakeCooks.stream()
                .filter(cook -> cook.getId().equals(cookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cook not found: " + cookId));
    }

    @Override
    public void update(Cook cook) {
        Cook existingCook = get(cook.getId());
        fakeCooks.remove(existingCook);
        fakeCooks.add(cook);
    }

    @Override
    public void add(Cook cook) {
        fakeCooks.add(cook);
    }

    @Override
    public void reset() {
        fakeCooks.clear();
    }
}
