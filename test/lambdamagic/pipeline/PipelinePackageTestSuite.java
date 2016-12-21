package lambdamagic.pipeline;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.pipeline.composite.FilteredDataSourceTest;
import lambdamagic.pipeline.composite.InterleavedDataSourceTest;
import lambdamagic.pipeline.composite.MergedDataSourceTest;
import lambdamagic.pipeline.composite.TrimmedDataSourceTest;
import lambdamagic.pipeline.composite.ZippedDataSourceTest;

@RunWith(Suite.class)
@SuiteClasses(
	{
		PipelineTest.class,
		FilteredDataSourceTest.class,
		InterleavedDataSourceTest.class,
		MergedDataSourceTest.class,
		TrimmedDataSourceTest.class,
		ZippedDataSourceTest.class
	})
public class PipelinePackageTestSuite {}
