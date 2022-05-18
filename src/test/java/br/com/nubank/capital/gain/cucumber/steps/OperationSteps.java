package br.com.nubank.capital.gain.cucumber.steps;

import br.com.nubank.capital.gain.Application;
import br.com.nubank.capital.gain.delivery.controller.OperationController;
import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.domain.entity.Tax;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void init(){
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
        for (String operation: operations) {
            operationController.execute(operation);
        }
    }

    @Then("the stdout should return the following values:")
    public void theStdoutShouldReturnTheFollowingValues(List<String> expectedResultList) throws JsonProcessingException {
        List<String> resultList = Arrays.stream(outContent.toString().split(NEW_LINE)).toList();

        for(int i=0; i < expectedResultList.size(); i++){
            Assertions.assertEquals(expectedResultList.get(i), resultList.get(i));
        }
    }

    ///TODO criar feature para teste de arrendondamento de decimais

    private String compactJson(String json) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
        return jsonNode.toString();
    }

}
