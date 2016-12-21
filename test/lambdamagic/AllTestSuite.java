package lambdamagic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lambdamagic.collection.CollectionPackageTestSuite;
import lambdamagic.concurrency.ConcurrencyPackageTestSuite;
import lambdamagic.csv.CSVPackageTestSuite;
import lambdamagic.data.DataPackageTestSuite;
import lambdamagic.event.EventPackageTestSuite;
import lambdamagic.io.IOPackageTestSuite;
import lambdamagic.json.JSONPackageTestSuite;
import lambdamagic.parsing.ParsingPackageTestSuite;
import lambdamagic.pipeline.PipelinePackageTestSuite;
import lambdamagic.resource.ResourcePackageTestSuite;
import lambdamagic.settings.SettingsPackageTestSuite;
import lambdamagic.sql.SQLPackageTestSuite;
import lambdamagic.text.TextPackageTestSuite;
import lambdamagic.web.WebPackageTestSuite;

@RunWith(Suite.class)
@SuiteClasses(
	{
		CollectionPackageTestSuite.class,
		ConcurrencyPackageTestSuite.class,
		CSVPackageTestSuite.class,
		DataPackageTestSuite.class,
		EventPackageTestSuite.class,
		IOPackageTestSuite.class,
		JSONPackageTestSuite.class,
		ParsingPackageTestSuite.class,
		PipelinePackageTestSuite.class,
		ResourcePackageTestSuite.class,
		SettingsPackageTestSuite.class,
		SQLPackageTestSuite.class,
		TextPackageTestSuite.class,
		WebPackageTestSuite.class,
	})
public class AllTestSuite {}
