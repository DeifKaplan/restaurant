package ch.kplan.adapter.persistence.cook;

import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.cook.CookId;
import ch.kplan.domain.cook.CookRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class JpaCookRepository implements CookRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CookEntityMapper mapper;

    @Override
    public Optional<Cook> findIdleCook() {
        return entityManager.createQuery("""
                        SELECT c
                        FROM CookEntity c
                        WHERE c.orderId IS NULL
                        """, CookEntity.class)
                .getResultStream()
                .map(mapper::toDomain)
                .findFirst();
    }

    @Override
    public Cook get(CookId cookId) {
        return findEntity(cookId)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Cook not found: " + cookId));
    }

    @Override
    public void update(Cook cook) {
        if (findEntity(cook.getId()).isEmpty()) {
            throw new IllegalArgumentException("Cook not found: " + cook);
        }
        entityManager.merge(mapper.toEntity(cook));
    }

    @Override
    public void add(Cook cook) {
        CookEntity entity = mapper.toEntity(cook);
        entityManager.persist(entity);
    }

    private Optional<CookEntity> findEntity(CookId cookId) {
        return Optional.ofNullable(entityManager.find(CookEntity.class, cookId.value()));
    }
}
