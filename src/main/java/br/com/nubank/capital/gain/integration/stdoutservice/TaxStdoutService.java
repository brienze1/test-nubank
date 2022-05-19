package br.com.nubank.capital.gain.integration.stdoutservice;

import br.com.nubank.capital.gain.domain.adapter.SendTaxServiceAdapter;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.integration.exception.StdoutException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

    private ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

    @Override
    public void send(ArrayList<Tax> taxList) {
        try {
            System.out.println(mapper.writeValueAsString(taxList));
        } catch (JsonProcessingException ex) {
            throw new StdoutException(ex);
        }
    }

}
