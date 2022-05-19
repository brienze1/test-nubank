package br.com.nubank.capital.gain.integration.stdout;

import br.com.nubank.capital.gain.domain.adapter.SendTaxServiceAdapter;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.integration.dto.TaxDto;
import br.com.nubank.capital.gain.integration.exception.StdoutException;
import br.com.nubank.capital.gain.integration.parse.TaxParse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class TaxStdoutService implements SendTaxServiceAdapter {

    private static TaxStdoutService instance;

    private TaxStdoutService() {
    }

    public static TaxStdoutService getInstance() {
        if (instance == null) {
            instance = new TaxStdoutService();
        }
        return instance;
    }

    private ObjectMapper mapper = new ObjectMapper();
    private TaxParse taxParse = TaxParse.getInstance();

    @Override
    public void send(ArrayList<Tax> taxList) {
        ArrayList<TaxDto> taxDtoList = taxParse.toTaxDtoList(taxList);

        try {
            System.out.println(mapper.writeValueAsString(taxDtoList));
        } catch (JsonProcessingException ex) {
            throw new StdoutException(ex);
        }
    }

}
