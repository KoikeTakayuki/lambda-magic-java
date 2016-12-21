package lambdamagic.csv;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
	{
		CSVDataSourceTest.class,
		CSVParserTest.class,
		CSVWriterTest.class,
	})
public class CSVPackageTestSuite {}
