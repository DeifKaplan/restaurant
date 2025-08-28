package ch.kplan.domain.waiter;

public interface WaiterRepository {

    Waiter get(WaiterId id);

    void add(Waiter waiter);
}
