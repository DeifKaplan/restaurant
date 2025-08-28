package ch.kplan.application.acceptancetest;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

public class AcceptanceTestExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        Map<String, StatefulFake> beans = applicationContext.getBeansOfType(StatefulFake.class);
        for (StatefulFake fake : beans.values()) {
            fake.reset();
        }
    }
}
