package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.validators.Validators;

import java.util.ArrayList;

public class OperationSet {

    private ArrayList<Operation> operations;

    public OperationSet(ArrayList<Operation> operations) {
        Validators.validateNotEmpty(operations, "Operation array cannot be empty");
        this.operations = operations;
    }

}
