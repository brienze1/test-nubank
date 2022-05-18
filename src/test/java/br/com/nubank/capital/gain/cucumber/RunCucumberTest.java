package br.com.nubank.capital.gain.cucumber;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"br.com.nubank.capital.gain.cucumber.steps"},
        plugin = "pretty")
public class RunCucumberTest {

}
