package ch.kplan.domain.waiter;

public class Waiter {

    private final WaiterId id;
    private final String name;

    public Waiter(WaiterBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public WaiterId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class WaiterBuilder {
        private WaiterId id;
        private String name;

        private WaiterBuilder() {

        }

        public WaiterBuilder withId(WaiterId id) {
            this.id = id;
            return this;
        }

        public WaiterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Waiter build() {
            return new Waiter(this);
        }
    }
}
