package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.entity.enums.OperationType;
import br.com.nubank.capital.gain.domain.entity.validators.Validators;

import java.math.BigDecimal;

public class Operation {

    private OperationType operationType;
    private BigDecimal unitCost;
    private BigDecimal quantity;

    public Operation(OperationType operationType, BigDecimal unitCost, BigDecimal quantity) {
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getOperationValue() {
        return quantity.multiply(unitCost);
    }

    public boolean valueIsGreaterThan(BigDecimal value) {
        return getOperationValue().compareTo(value) > 0;
    }
}
