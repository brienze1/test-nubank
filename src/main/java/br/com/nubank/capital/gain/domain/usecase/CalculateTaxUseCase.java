package br.com.nubank.capital.gain.domain.usecase;

import br.com.nubank.capital.gain.domain.entity.Operation;
import br.com.nubank.capital.gain.domain.entity.OperationSet;
import br.com.nubank.capital.gain.domain.entity.Tax;

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


        return null;
    }

}
