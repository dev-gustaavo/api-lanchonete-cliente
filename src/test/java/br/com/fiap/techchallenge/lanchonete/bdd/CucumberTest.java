package br.com.fiap.techchallenge.lanchonete.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@CucumberContextConfiguration
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberTest {
}
