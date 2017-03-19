package jp.lambdamagic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.collection.CollectionPackageTestSuite;
import jp.lambdamagic.concurrency.ConcurrencyPackageTestSuite;
import jp.lambdamagic.csv.CSVPackageTestSuite;
import jp.lambdamagic.data.DataPackageTestSuite;
import jp.lambdamagic.event.EventPackageTestSuite;
import jp.lambdamagic.io.IOPackageTestSuite;
import jp.lambdamagic.json.JSONPackageTestSuite;
import jp.lambdamagic.pipeline.PipelinePackageTestSuite;
import jp.lambdamagic.resource.ResourcePackageTestSuite;
import jp.lambdamagic.settings.SettingsPackageTestSuite;
import jp.lambdamagic.sql.SQLPackageTestSuite;
import jp.lambdamagic.text.TextPackageTestSuite;
import jp.lambdamagic.web.WebPackageTestSuite;

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
        PipelinePackageTestSuite.class,
        ResourcePackageTestSuite.class,
        SettingsPackageTestSuite.class,
        SQLPackageTestSuite.class,
        TextPackageTestSuite.class,
        WebPackageTestSuite.class,
    })
public class AllTestSuite {}
