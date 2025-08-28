package ch.kplan.domain.cook;

import java.util.Optional;

public interface CookRepository {
    Optional<Cook> findIdleCook();

    Cook get(CookId cookId);

    void update(Cook cook);

    void add(Cook cook);
}
