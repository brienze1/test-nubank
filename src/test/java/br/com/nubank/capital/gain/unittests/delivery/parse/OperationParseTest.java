package br.com.nubank.capital.gain.unittests.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.OperationDto;
import br.com.nubank.capital.gain.delivery.parse.OperationParse;
import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.enums.OperationType;
import br.com.nubank.capital.gain.domain.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class OperationParseTest {

    @InjectMocks
    private OperationParse operationParse;

    private ArrayList<OperationDto> operationListDto;

    @Before
    public void init() {
        operationListDto = new ArrayList<>();
        operationListDto.add(new OperationDto(OperationType.BUY, BigDecimal.TEN, 1L));
    }

    @Test
    public void toOperationListTest() {
        ArrayList<Operation> operationList = operationParse.toOperationList(operationListDto);

        Assert.assertNotNull(operationList);
        Assert.assertFalse(operationList.isEmpty());
        Assert.assertEquals(operationList.size(), operationListDto.size());
        Assert.assertEquals(operationList.get(0).getOperationType(), operationListDto.get(0).getOperationType());
        Assert.assertEquals(operationList.get(0).getUnitCost(), operationListDto.get(0).getUnitCost());
        Assert.assertEquals(operationList.get(0).getQuantity(), operationListDto.get(0).getQuantity());
    }

    @Test
    public void toOperationListNullListTest() {
        ArrayList<Operation> operationList = operationParse.toOperationList(null);

        Assert.assertNotNull(operationList);
        Assert.assertTrue(operationList.isEmpty());
    }

    @Test
    public void toOperationListNullObjectTest() {
        operationListDto = new ArrayList<>();
        operationListDto.add(null);

        ValidationException exception = Assert.assertThrows(ValidationException.class, () -> operationParse.toOperationList(operationListDto));

        Assert.assertNotNull(exception);
        Assert.assertEquals("Operation dto cannot be null", exception.getMessage());
    }

}
