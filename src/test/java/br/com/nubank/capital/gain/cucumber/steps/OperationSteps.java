package br.com.nubank.capital.gain.cucumber.steps;

import br.com.nubank.capital.gain.delivery.controller.OperationController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperationSteps {

    private static final String NEW_LINE = "\n";

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ArrayList<String> operations;

    private static final OperationController operationController = OperationController.getInstance();

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        operations = new ArrayList<>();
    }

    @After
    public void finish() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Given("the following operation:")
    public void theFollowingOperation(String operationJson) throws JsonProcessingException {
        operations.add(compactJson(operationJson));
    }

    @And("also the following operation:")
    public void alsoTheFollowingOperation(String operationJson) throws JsonProcessingException {
        operations.add(compactJson(operationJson));
    }

    @When("I type in the command lines")
    public void iTypeInTheCommandLines() {
        for (String operation : operations) {
            operationController.execute(operation);
        }
    }

    @Then("the stdout should return the following values:")
    public void theStdoutShouldReturnTheFollowingValues(List<String> expectedResultList) {
        List<String> resultList = Arrays.stream(outContent.toString().replace("\r", "").split(NEW_LINE)).collect(Collectors.toList());

        for (int i = 0; i < expectedResultList.size(); i++) {
            Assert.assertEquals(expectedResultList.get(i), resultList.get(i));
        }
    }

    ///TODO criar feature para teste de arrendondamento de decimais

    private String compactJson(String json) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        return jsonNode.toString();
    }

}
