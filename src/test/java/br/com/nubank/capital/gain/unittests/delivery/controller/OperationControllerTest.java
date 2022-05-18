package br.com.nubank.capital.gain.unittests.delivery.controller;

import br.com.nubank.capital.gain.delivery.controller.OperationController;
import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.delivery.dto.TaxDto;
import br.com.nubank.capital.gain.delivery.parse.OperationParse;
import br.com.nubank.capital.gain.delivery.parse.TaxParse;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.domain.enums.OperationType;
import br.com.nubank.capital.gain.domain.exception.OperationException;
import br.com.nubank.capital.gain.domain.exception.ValidationException;
import br.com.nubank.capital.gain.domain.usecase.CalculateTaxUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class OperationControllerTest {

    private static final String INPUT = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]";

    @InjectMocks
    private OperationController operationController;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private OperationParse operationParse;

    @Mock
    private CalculateTaxUseCase calculateTaxUseCase;

    @Mock
    private TaxParse taxParse;

    @Captor
    private ArgumentCaptor<OperationSet> operationSetCaptor;

    private ArrayList<OperationDto> operationDtoList;
    private ArrayList<Operation> operationList;
    private ArrayList<Tax> taxes;
    private ArrayList<TaxDto> taxesDto;

    @Before
    public void init(){
        operationDtoList = new ArrayList<>();
        operationDtoList.add(new OperationDto(OperationType.BUY, BigDecimal.TEN, 10L));

        operationList = new ArrayList<>();
        operationList.add(new Operation(OperationType.BUY, BigDecimal.TEN, 10L));

        taxes = new ArrayList<>();
        taxes.add(new Tax(new BigDecimal("10.00")));

        taxesDto = new ArrayList<>();
        taxesDto.add(new TaxDto(new BigDecimal("10.00")));
    }

    @Test
    public void executeSingletonTest() {
        OperationController operationController1 = OperationController.getInstance();
        OperationController operationController2 = OperationController.getInstance();

        Assert.assertEquals(operationController1, operationController2);
    }

    @Test
    public void executeTest() throws JsonProcessingException {
        Mockito.when(mapper.readValue(Mockito.eq(INPUT), ArgumentMatchers.<TypeReference<ArrayList<OperationDto>>>any())).thenReturn(operationDtoList);
        Mockito.when(operationParse.toOperationList(operationDtoList)).thenReturn(operationList);
        Mockito.when(calculateTaxUseCase.calculateTax(operationSetCaptor.capture())).thenReturn(taxes);
        Mockito.when(taxParse.toTaxDtoList(taxes)).thenReturn(taxesDto);

        ArrayList<TaxDto> response = operationController.execute(INPUT);

        Mockito.verify(mapper, Mockito.times(1)).readValue(Mockito.eq(INPUT), ArgumentMatchers.<TypeReference<ArrayList<OperationDto>>>any());
        Mockito.verify(operationParse, Mockito.times(1)).toOperationList(operationDtoList);
        Mockito.verify(calculateTaxUseCase, Mockito.times(1)).calculateTax(operationSetCaptor.capture());
        Mockito.verify(taxParse, Mockito.times(1)).toTaxDtoList(taxes);

        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(taxesDto.get(0).getTax(), response.get(0).getTax());
    }

    @Test
    public void executeFailureTest() throws JsonProcessingException {
        Mockito.when(mapper.readValue(Mockito.eq(INPUT), ArgumentMatchers.<TypeReference<ArrayList<OperationDto>>>any())).thenReturn(operationDtoList);
        Mockito.when(operationParse.toOperationList(operationDtoList)).thenReturn(operationList);
        Mockito.when(calculateTaxUseCase.calculateTax(operationSetCaptor.capture())).thenThrow(new ValidationException("Error"));

        OperationException exception = Assert.assertThrows(OperationException.class, () -> operationController.execute(INPUT));

        Mockito.verify(mapper, Mockito.times(1)).readValue(Mockito.eq(INPUT), ArgumentMatchers.<TypeReference<ArrayList<OperationDto>>>any());
        Mockito.verify(operationParse, Mockito.times(1)).toOperationList(operationDtoList);
        Mockito.verify(calculateTaxUseCase, Mockito.times(1)).calculateTax(operationSetCaptor.capture());
        Mockito.verifyNoInteractions(taxParse);

        Assert.assertNotNull(exception);
        Assert.assertEquals("Operation set could not be executed", exception.getMessage());
        Assert.assertEquals("Error", exception.getCause().getMessage());
        Assert.assertEquals(ValidationException.class, exception.getCause().getClass());
    }

}
