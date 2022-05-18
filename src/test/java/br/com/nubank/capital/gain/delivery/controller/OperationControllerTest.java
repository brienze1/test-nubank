package br.com.nubank.capital.gain.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
public class OperationControllerTest {

    private static final String INPUT = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private OperationController operationController;

    @BeforeEach
    public void init(){
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void finish() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void executeTest() throws JsonProcessingException {
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenReturn("a");

        operationController.execute(INPUT);

        Assertions.assertEquals("a\n", outContent.toString());
    }

}
