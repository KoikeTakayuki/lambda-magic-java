package jp.lambdamagic.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jp.lambdamagic.web.http.service.rest.HttpRestOperationTest;
import jp.lambdamagic.web.http.service.rest.HttpRestParameterTest;

@RunWith(Suite.class)
@SuiteClasses(
    {
        HttpRestOperationTest.class,
        HttpRestParameterTest.class
    })
public class WebPackageTestSuite {}
