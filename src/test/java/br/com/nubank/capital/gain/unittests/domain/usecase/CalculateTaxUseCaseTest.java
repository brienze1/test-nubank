package br.com.nubank.capital.gain.unittests.domain.usecase;

import br.com.nubank.capital.gain.domain.adapter.SendTaxServiceAdapter;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.domain.entity.enums.OperationType;
import br.com.nubank.capital.gain.domain.usecase.CalculateTaxUseCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CalculateTaxUseCaseTest {

    @InjectMocks
    private CalculateTaxUseCase calculateTaxUseCase;

    @Mock
    private SendTaxServiceAdapter sendTaxService;

    private OperationSet operationSet;

    @Before
    public void init() {
        ArrayList<Operation> operationList = new ArrayList<>();
        operationList.add(new Operation(OperationType.BUY, new BigDecimal(10), new BigDecimal(10000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(2), new BigDecimal(5000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(20), new BigDecimal(2000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(20), new BigDecimal(2000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(25), new BigDecimal(1000)));
        operationList.add(new Operation(OperationType.BUY, new BigDecimal(20), new BigDecimal(10000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(15), new BigDecimal(5000)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(30), new BigDecimal(4350)));
        operationList.add(new Operation(OperationType.SELL, new BigDecimal(30), new BigDecimal(650)));

        operationSet = new OperationSet(operationList);
    }

    @Test
    public void calculateTaxTest() {
        ArrayList<Tax> taxList = calculateTaxUseCase.calculateTax(operationSet);

        Mockito.verify(sendTaxService, Mockito.times(1)).send(taxList);

        Assert.assertNotNull(taxList);
        Assert.assertEquals(taxList.size(), 9);
        Assert.assertEquals(taxList.get(0), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(1), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(2), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(3), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(4), new Tax(new BigDecimal(3000)));
        Assert.assertEquals(taxList.get(5), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(6), new Tax(new BigDecimal(0)));
        Assert.assertEquals(taxList.get(7), new Tax(new BigDecimal(3700)));
        Assert.assertEquals(taxList.get(8), new Tax(new BigDecimal(0)));
    }

}
