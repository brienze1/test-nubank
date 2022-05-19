package br.com.nubank.capital.gain.unittests.integration.stdout;

import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.integration.dto.TaxDto;
import br.com.nubank.capital.gain.integration.exception.StdoutException;
import br.com.nubank.capital.gain.integration.parse.TaxParse;
import br.com.nubank.capital.gain.integration.stdout.TaxStdoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TaxStdoutServiceTest {

    @InjectMocks
    private TaxStdoutService taxStdoutService;

    @Mock
    private TaxParse taxParse;

    @Spy
    private ObjectMapper objectMapper;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private ArrayList<Tax> taxList;
    private ArrayList<TaxDto> taxDtoList;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));

        taxList = new ArrayList<>();
        taxList.add(new Tax(new BigDecimal(10)));
        taxList.add(new Tax(new BigDecimal(20)));
        taxList.add(new Tax(new BigDecimal(30)));
        taxList.add(new Tax(new BigDecimal(40)));
        taxList.add(new Tax(new BigDecimal(0)));
        taxList.add(new Tax(new BigDecimal(50)));

        taxDtoList = new ArrayList<>();
        taxDtoList.add(new TaxDto(taxList.get(0).getTax()));
        taxDtoList.add(new TaxDto(taxList.get(1).getTax()));
        taxDtoList.add(new TaxDto(taxList.get(2).getTax()));
        taxDtoList.add(new TaxDto(taxList.get(3).getTax()));
        taxDtoList.add(new TaxDto(taxList.get(4).getTax()));
        taxDtoList.add(new TaxDto(taxList.get(5).getTax()));
    }

    @After
    public void finish() {
        System.setOut(originalOut);
    }

    @Test
    public void sendTest() throws JsonProcessingException {
        Mockito.when(taxParse.toTaxDtoList(taxList)).thenReturn(taxDtoList);

        taxStdoutService.send(taxList);

        ArrayList<TaxDto> resultList = mapper.readValue(outContent.toString(), new TypeReference<>() {});

        Mockito.verify(taxParse, Mockito.times(1)).toTaxDtoList(taxList);

        Assert.assertNotNull(resultList);
        Assert.assertFalse(resultList.isEmpty());
        Assert.assertEquals(resultList.size(), taxList.size());

        for(int i=0; i<taxList.size(); i++) {
            Assert.assertEquals(resultList.get(i).getTax(), taxList.get(i).getTax());
        }
    }

    @Test
    public void sendErrorTest() throws JsonProcessingException {
        taxDtoList = null;
        Mockito.when(taxParse.toTaxDtoList(taxList)).thenReturn(taxDtoList);
        Mockito.when(objectMapper.writeValueAsString(taxDtoList)).thenThrow(new JsonProcessingException("Error") {});

        StdoutException exception = Assert.assertThrows(StdoutException.class, () -> taxStdoutService.send(taxList));

        Mockito.verify(taxParse, Mockito.times(1)).toTaxDtoList(taxList);

        Assert.assertNotNull(exception);
        Assert.assertEquals("Operation set could not be executed", exception.getMessage());
    }

}
