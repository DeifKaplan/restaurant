package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.waiter.LoggedInWaiterService;
import ch.kplan.domain.waiter.WaiterId;
import org.springframework.stereotype.Service;

@Service
public class FakeLoggedInWaiterService implements LoggedInWaiterService, StatefulFake {

    private WaiterId loggedInWaiterId;

    @Override
    public WaiterId getLoggedInWaiterId() {
        return loggedInWaiterId;
    }

    public void setLoggedInWaiterId(WaiterId loggedInWaiterId) {
        this.loggedInWaiterId = loggedInWaiterId;
    }

    @Override
    public void reset() {
        loggedInWaiterId = null;
    }
}
