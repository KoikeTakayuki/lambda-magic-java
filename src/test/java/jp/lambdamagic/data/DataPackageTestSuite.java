package jp.lambdamagic.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.data.functional.EitherTest;

@RunWith(Suite.class)
@SuiteClasses(
    {
        Tuple2Test.class,
        InMemoryPropertySetTest.class,
        EitherTest.class
    })
public class DataPackageTestSuite {}
