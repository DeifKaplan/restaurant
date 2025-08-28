package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.table.Table;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.table.TableRepository;

import java.util.HashSet;
import java.util.Set;

public class FakeTableRepository implements TableRepository, StatefulFake {

    private final Set<Table> fakeTables = new HashSet<>();

    @Override
    public Table get(TableId id) {
        return fakeTables.stream()
                .filter(table -> table.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Table not found: " + id));
    }

    @Override
    public void add(Table table) {
        fakeTables.add(table);
    }

    @Override
    public void reset() {
        fakeTables.clear();
    }
}
