package br.com.nubank.capital.gain.delivery.dto;

import br.com.nubank.capital.gain.domain.entity.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class OperationDto {

    @JsonProperty("operation")
    private OperationType operationType;

    @JsonProperty("unit-cost")
    private BigDecimal unitCost;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    public OperationDto() {}

    public OperationDto(OperationType operationType, BigDecimal unitCost, BigDecimal quantity) {
        this.operationType = operationType;
        this.unitCost = unitCost;
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
}
