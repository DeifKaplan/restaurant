package ch.kplan.application.acceptancetest.fake;

import ch.kplan.application.acceptancetest.StatefulFake;
import ch.kplan.domain.cook.CookId;
import ch.kplan.domain.cook.LoggedInCookService;
import org.springframework.stereotype.Service;

@Service
public class FakeLoggedInCookService implements LoggedInCookService, StatefulFake {

    private CookId loggedInCookId;

    @Override
    public CookId getLoggedInCookId() {
        return loggedInCookId;
    }

    public void setLoggedInCookId(CookId loggedInCookId) {
        this.loggedInCookId = loggedInCookId;
    }

    @Override
    public void reset() {
        loggedInCookId = null;
    }
}
