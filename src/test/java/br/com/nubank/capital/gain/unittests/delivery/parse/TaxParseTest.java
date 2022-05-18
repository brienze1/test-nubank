package br.com.nubank.capital.gain.unittests.delivery.parse;

import br.com.nubank.capital.gain.delivery.dto.TaxDto;
import br.com.nubank.capital.gain.delivery.parse.TaxParse;
import br.com.nubank.capital.gain.domain.entity.Tax;
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
public class TaxParseTest {

    @InjectMocks
    private TaxParse taxParse;

    private ArrayList<Tax> taxList;

    @Before
    public void init(){
        taxList = new ArrayList<>();
        taxList.add(new Tax(BigDecimal.TEN));
    }

    @Test
    public void toTaxDtoListTest(){
        ArrayList<TaxDto> taxDtoList = taxParse.toTaxDtoList(taxList);

        Assert.assertNotNull(taxDtoList);
        Assert.assertFalse(taxDtoList.isEmpty());
        Assert.assertEquals(taxDtoList.size(), taxList.size());
        Assert.assertEquals(taxDtoList.get(0).getTax(), taxList.get(0).getTax());
    }

    @Test
    public void toTaxDtoListNullListTest() {
        ArrayList<TaxDto> taxDtoList = taxParse.toTaxDtoList(null);

        Assert.assertNotNull(taxDtoList);
        Assert.assertTrue(taxDtoList.isEmpty());
    }

    @Test
    public void toTaxDtoListNullObjectTest() {
        taxList = new ArrayList<>();
        taxList.add(null);

        ValidationException exception = Assert.assertThrows(ValidationException.class, () -> taxParse.toTaxDtoList(taxList));

        Assert.assertNotNull(exception);
        Assert.assertEquals("Tax dto cannot be null", exception.getMessage());
    }

}
