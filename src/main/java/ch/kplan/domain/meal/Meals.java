package ch.kplan.domain.meal;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record Meals(List<Meal> value) implements Iterable<Meal> {

    @Override
    public Iterator<Meal> iterator() {
        return value.iterator();
    }

    public Stream<Meal> stream() {
        return value.stream();
    }
}
