package br.com.nubank.capital.gain.domain.usecase;

import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.entity.Tax;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CalculateTaxUseCase {

    private static CalculateTaxUseCase instance;

    private CalculateTaxUseCase() {}

    public static CalculateTaxUseCase getInstance() {
        if(instance == null) {
            instance = new CalculateTaxUseCase();
        }
        return instance;
    }

    public ArrayList<Tax> calculateTax(OperationSet operationSet) {
        ArrayList<Tax> taxes = new ArrayList<>();

        taxes.add(new Tax(new BigDecimal("10.00")));

        return taxes;
    }

}
