package br.com.nubank.capital.gain.delivery.dto;

import br.com.nubank.capital.gain.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class OperationDto {

    @JsonProperty("operation")
    private OperationType operationType;

    @JsonProperty("unit-cost")
    private BigDecimal unitCost;

    @JsonProperty("quantity")
    private Long quantity;

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
