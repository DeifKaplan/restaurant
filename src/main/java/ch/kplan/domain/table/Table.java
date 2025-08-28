package ch.kplan.domain.table;

import java.util.Objects;

public class Table {

    private final TableId id;
    private final int numberOfSeats;

    public Table(TableBuilder builder) {
        this.id = builder.id;
        this.numberOfSeats = builder.numberOfSeats;
    }

    public TableId getId() {
        return id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }

    public static class TableBuilder {
        private TableId id;
        private int numberOfSeats;

        private TableBuilder() {

        }

        public TableBuilder withId(TableId id) {
            this.id = id;
            return this;
        }

        public TableBuilder withNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Table build() {
            return new Table(this);
        }
    }
}
