package jp.lambdamagic.pipeline;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
  {
    PipelineTest.class,
    FilteredDataSourceTest.class,
    InterleavedDataSourceTest.class,
    MergedDataSourceTest.class,
    RepetitiveDataSourceTest.class,
    TrimmedDataSourceTest.class,
    ZippedDataSourceTest.class
  })
public class PipelinePackageTestSuite {}
