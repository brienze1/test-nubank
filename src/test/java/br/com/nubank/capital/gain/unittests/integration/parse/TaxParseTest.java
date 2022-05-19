package br.com.nubank.capital.gain.unittests.integration.parse;

import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.integration.dto.TaxDto;
import br.com.nubank.capital.gain.integration.parse.TaxParse;
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
    public void init() {
        taxList = new ArrayList<>();
        taxList.add(new Tax(new BigDecimal(10)));
        taxList.add(new Tax(new BigDecimal(20)));
        taxList.add(new Tax(new BigDecimal(30)));
        taxList.add(new Tax(new BigDecimal(40)));
        taxList.add(new Tax(new BigDecimal(0)));
        taxList.add(new Tax(new BigDecimal(50)));
    }

    @Test
    public void Test() {
        ArrayList<TaxDto> taxDtoList = taxParse.toTaxDtoList(taxList);

        Assert.assertNotNull(taxDtoList);
        Assert.assertFalse(taxDtoList.isEmpty());
        Assert.assertEquals(taxDtoList.size(), taxList.size());

        for(int i=0; i<taxList.size(); i++) {
            Assert.assertEquals(taxDtoList.get(i).getTax(), taxList.get(i).getTax());
        }

    }

}
