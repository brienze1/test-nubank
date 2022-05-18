package br.com.nubank.capital.gain.domain.entity;

import br.com.nubank.capital.gain.domain.validators.Validators;

import java.math.BigDecimal;

public class Tax {
    private final BigDecimal tax;

    public Tax(BigDecimal tax) {
        Validators.validateGreaterThanZero(tax, "Tax value needs to be greater than zero");
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
