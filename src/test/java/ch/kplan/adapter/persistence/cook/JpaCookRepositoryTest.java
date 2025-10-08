package ch.kplan.adapter.persistence.cook;

import ch.kplan.domain.a;
import ch.kplan.domain.cook.Cook;
import ch.kplan.domain.order.OrderId;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaCookRepositoryTest {

    @Mock
    private CookEntityMapper mapperMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private EntityManager entityManagerMock;

    @InjectMocks
    private JpaCookRepository sut;

    @Test
    void addCook() {
        Cook cook = a.Cook().build();
        CookEntity cookEntityMock = mock(CookEntity.class);
        when(mapperMock.toEntity(cook)).thenReturn(cookEntityMock);

        sut.add(cook);

        verify(entityManagerMock).persist(cookEntityMock);
    }
    @Test
    void getCook() {
        Cook cook = a.Cook().build();
        CookEntity cookEntityMock = mock(CookEntity.class);
        when(mapperMock.toDomain(cookEntityMock)).thenReturn(cook);
        when(entityManagerMock.find(CookEntity.class, cook.getId().value())).thenReturn(cookEntityMock);

        Cook actual = sut.get(cook.getId());

        assertThat(actual).isEqualTo(cook);
    }

    @Test
    void updateCook() {
        Cook cook = a.Cook().build();
        CookEntity cookEntityMock = mock(CookEntity.class);
        when(mapperMock.toEntity(cook)).thenReturn(cookEntityMock);
        when(entityManagerMock.find(CookEntity.class, cook.getId().value())).thenReturn(cookEntityMock);

        sut.update(cook);

        verify(entityManagerMock).merge(cookEntityMock);
    }

    @Test
    void findIdleCook() {
        Cook cook = a.Cook().build();
        CookEntity cookEntityMock = mock(CookEntity.class);
        when(mapperMock.toDomain(cookEntityMock)).thenReturn(cook);
        when(entityManagerMock.createQuery(any(), eq(CookEntity.class)).getResultStream()).thenReturn(Stream.of(cookEntityMock));

        Optional<Cook> actual = sut.findIdleCook();

        assertThat(actual).contains(cook);
    }
}
