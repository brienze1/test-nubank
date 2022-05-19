package br.com.nubank.capital.gain.domain.usecase;

import br.com.nubank.capital.gain.domain.adapter.SendTaxServiceAdapter;
import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.entity.Tax;
import br.com.nubank.capital.gain.integration.stdoutservice.TaxStdoutService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalculateTaxUseCase {

    private static CalculateTaxUseCase instance;

    private CalculateTaxUseCase() {}

    public static CalculateTaxUseCase getInstance() {
        if(instance == null) {
            instance = new CalculateTaxUseCase();
        }
        return instance;
    }

    private static final BigDecimal OPERATION_VALUE_CAP_WITHOUT_TAX = new BigDecimal("20000");
    private static final BigDecimal TAX_PERCENTAGE = new BigDecimal("0.2");

    private SendTaxServiceAdapter sendTaxService = TaxStdoutService.getInstance();

    public ArrayList<Tax> calculateTax(OperationSet operationSet) {
        ArrayList<Tax> taxList = operationSet
                .generateTax(OPERATION_VALUE_CAP_WITHOUT_TAX, TAX_PERCENTAGE)
                .stream()
                .map(Tax::new)
                .collect(Collectors.toCollection(ArrayList::new));

        sendTaxService.send(taxList);

        return taxList;
    }

}
