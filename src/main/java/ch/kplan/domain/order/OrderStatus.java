package ch.kplan.domain.order;

public enum OrderStatus {
    PENDING {
        @Override
        public OrderStatus inProgress() {
            return IN_PROGRESS;
        }

        @Override
        public boolean isPending() {
            return true;
        }
    },
    IN_PROGRESS {
        @Override
        public OrderStatus completed() {
            return COMPLETED;
        }
    },
    COMPLETED;

    public OrderStatus inProgress() {
        throw new IllegalStateException("Cannot transition from " + this + " to IN_PROGRESS");
    }

    public OrderStatus completed() {
        throw new IllegalStateException("Cannot transition from " + this + " to COMPLETED");
    }

    public boolean isPending() {
        return false;
    }
}
