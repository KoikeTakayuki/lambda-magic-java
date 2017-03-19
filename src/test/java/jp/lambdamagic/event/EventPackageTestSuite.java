package jp.lambdamagic.event;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
    {
        EventListenerManagerTest.class,
        NotificationEventTest.class,
        ProgressEventTest.class,
    })
public class EventPackageTestSuite {}
