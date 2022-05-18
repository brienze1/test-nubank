package br.com.nubank.capital.gain.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TaxDto {

    @JsonProperty("tax")
    private final BigDecimal tax;

    public TaxDto(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }

}
