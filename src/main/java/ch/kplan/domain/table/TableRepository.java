package ch.kplan.domain.table;

public interface TableRepository {

    Table get(TableId id);

    void add(Table table);
}
