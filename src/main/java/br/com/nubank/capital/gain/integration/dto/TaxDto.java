package br.com.nubank.capital.gain.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TaxDto {

    @JsonProperty("tax")
    private BigDecimal tax;

    public TaxDto() {}

    public TaxDto(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
