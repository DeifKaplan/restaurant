package ch.kplan.domain.meal;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record Meals(List<MealId> value) implements Iterable<MealId> {

    @Override
    public Iterator<MealId> iterator() {
        return value.iterator();
    }

    public Stream<MealId> stream() {
        return value.stream();
    }
}
