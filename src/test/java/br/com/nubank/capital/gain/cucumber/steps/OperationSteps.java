package br.com.nubank.capital.gain.cucumber.steps;

import br.com.nubank.capital.gain.Application;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperationSteps {

    private static final String NEW_LINE = "\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ArrayList<String> operations;

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        operations = new ArrayList<>();
    }

    @After
    public void finish() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Given("the following operation:")
    public void theFollowingOperation(String operationJson) {
        operations.add(compactJson(operationJson));
    }

    @And("also the following operation:")
    public void alsoTheFollowingOperation(String operationJson) {
        operations.add(compactJson(operationJson));
    }

    @When("I type in the command lines")
    public void iTypeInTheCommandLines() {
        for (String operation : operations) {
            ByteArrayInputStream inContent = new ByteArrayInputStream(operation.getBytes());
            System.setIn(inContent);

            Application.main(new String[0]);
        }
    }

    @Then("the stdout should return the following values:")
    public void theStdoutShouldReturnTheFollowingValues(List<String> expectedResultList) {
        List<String> resultList = Arrays
                .stream(outContent.toString().replace("\r", "").split(NEW_LINE))
                .map(this::compactJson)
                .collect(Collectors.toList());

        for (int i = 0; i < expectedResultList.size(); i++) {
            Assert.assertEquals(compactJson(expectedResultList.get(i)), resultList.get(i));
        }
    }

    ///TODO criar feature para teste de arrendondamento de decimais

    private String compactJson(String json) {
        return json.replaceAll(NEW_LINE, "").replaceAll(" ", "");
    }

}
