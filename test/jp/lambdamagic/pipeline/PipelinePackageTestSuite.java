package jp.lambdamagic.pipeline;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.pipeline.wrapper.FilteredDataSourceTest;
import jp.lambdamagic.pipeline.wrapper.InterleavedDataSourceTest;
import jp.lambdamagic.pipeline.wrapper.MergedDataSourceTest;
import jp.lambdamagic.pipeline.wrapper.RepetitiveDataSourceTest;
import jp.lambdamagic.pipeline.wrapper.TrimmedDataSourceTest;
import jp.lambdamagic.pipeline.wrapper.ZippedDataSourceTest;

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
