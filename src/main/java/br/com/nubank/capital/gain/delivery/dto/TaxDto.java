package br.com.nubank.capital.gain.delivery.dto;

import br.com.nubank.capital.gain.domain.validators.Validators;

import java.math.BigDecimal;

public class TaxDto {

    private BigDecimal tax;

    public TaxDto(BigDecimal tax) {
        Validators.validateGreaterThanZero(tax, "Tax value needs to be greater than zero");
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }

}
