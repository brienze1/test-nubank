package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.enums.OperationType;
import br.com.nubank.capital.gain.domain.validators.Validators;

import java.math.BigDecimal;

public class Operation {

    private OperationType operationType;
    private BigDecimal unitCost;
    private Long quantity;

    public Operation(OperationType operationType, BigDecimal unitCost, Long quantity) {
        Validators.validateNotNull(operationType, "Operation type cannot be null");
        this.operationType = operationType;

        Validators.validateGreaterThanZero(unitCost, "Unit cost has to be greater than zero");
        this.unitCost = unitCost;

        Validators.validateGreaterThanZero(quantity, "Quantity has to be greater than zero");
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public Long getQuantity() {
        return quantity;
    }
}
