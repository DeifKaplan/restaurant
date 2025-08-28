package ch.kplan.domain.meal;

import java.util.Set;
import java.util.stream.Stream;

public record MealIds(Set<MealId> value) implements Iterable<MealId> {

    @Override
    public java.util.Iterator<MealId> iterator() {
        return value.iterator();
    }

    public Stream<MealId> stream() {
        return value.stream();
    }
}
